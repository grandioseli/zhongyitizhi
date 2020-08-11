package com.example.zhongyitizhi1.bluetooth;//package com.example.zhongyitizhi1.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
import com.zhuoting.health.Config;
import com.zhuoting.health.bean.BloodInfo;
import com.zhuoting.health.bean.ClockInfo;
import com.zhuoting.health.bean.HeartInfo;
import com.zhuoting.health.bean.SleepInfo;
import com.zhuoting.health.bean.SportInfo;
import com.zhuoting.health.notify.IDataResponse;
import com.zhuoting.health.notify.IErrorCommand;
import com.zhuoting.health.notify.IRequestResponse;
import com.zhuoting.health.parser.DataParser;
import com.zhuoting.health.parser.IOperation;
import com.zhuoting.health.util.DataUtil;
import com.zhuoting.health.util.Tools;
import com.zhuoting.health.util.TransUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MyBleService extends Service {

    UUID serviceUUID;
    private String mMac ;
    public static MyBleService myBleService ;
    public boolean isConnected = false;
    int datalenght = 0;
    byte[] databyte;
    @Override
    public void onCreate() {
        super.onCreate();
        myBleService = this ;
        ClientManager.getClient().registerBluetoothStateListener(bluetoothStateListener);
        DataParser.newInstance().setDataResponseListener(iDataResponse);
        DataParser.newInstance().setRequestResponseListener(iRequestResponse);
        DataParser.newInstance().setErrorCommandListener(iErrorCommand);
        DataParser.newInstance().setOperation(iOperation);
    }

    public static MyBleService getInstance(){
        if (myBleService == null){
            myBleService = new MyBleService();
            myBleService.onCreate();
        }
        return myBleService ;
    }
    //判断连接状态
    //返回值：true：已连接目标蓝牙，false：未连接目标蓝牙
    public boolean isConnect(){
        if( mMac == null ){
            return false ;
        }
        int connectStatus = ClientManager.getClient().getConnectStatus(mMac);
        return connectStatus == BluetoothProfile.STATE_CONNECTED;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private BluetoothStateListener bluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {

        }
    };

    /**
     * 蓝牙连接
     * @param mac       mac地址
     */
    public void connect(String mac){
        Log.d("tttt","我在连接");
        mMac = mac ;
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(1)   // 连接如果失败重试1次
                .setConnectTimeout(10000)   // 连接超时10s
                .setServiceDiscoverRetry(1)  // 发现服务如果失败重试1次
                .setServiceDiscoverTimeout(10000)  // 发现服务超时10s
                .build();
        ClientManager.getClient().connect(mac,options,bleConnectResponse);
        //监听蓝牙连接状态，注册回调，只有两个状态：连接和断开。
        ClientManager.getClient().registerConnectStatusListener(mac,connectStatusListener);
    }

    /**
     * 蓝牙断开
     */

    public void disConnect(String mac){
        ClientManager.getClient().disconnect(mac);
    }

    public void disConnect(){
        if( mMac == null ){
            return ;
        }
        disConnect(mMac);
    }
    //连接状态回调
    private BleConnectResponse bleConnectResponse = new BleConnectResponse() {
        @Override
        public void onResponse(int code, BleGattProfile data) {
            Log.e("connectRes","code = "+code);
                if( code == 0 ){        // 0 成功
                    isConnected = true;
                    setGattProfile(data);
                }else{
                    isConnected = false;
                }
        }
    };
    //监听蓝牙连接状态，注册回调，只有两个状态：连接和断开。
    private BleConnectStatusListener connectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            Log.e("connectStatus",status+"");
            //                    EventBus.getDefault().post(new ConnectBean(isConnected));
            //                    EventBus.getDefault().post(new ConnectBean(isConnected));
            isConnected = status == 0x10;
        }
    };

    public void setGattProfile(BleGattProfile profile) {
        List<String> items = new ArrayList<String>();
        List<BleGattService> services = profile.getServices();
        for (com.inuker.bluetooth.library.model.BleGattService service : services) {
//            Log.e("uuid","service : "+service.getUUID().toString());
            if(Config.char0.equalsIgnoreCase(service.getUUID().toString())){
                serviceUUID = service.getUUID();
                List<BleGattCharacter> characters = service.getCharacters();
                for(BleGattCharacter character : characters){
//                    String uuidCharacteristic = character.getUuid().toString();
//                    Log.e("uuid","characteristic : "+uuidCharacteristic);
                    if( character.getUuid().toString().equalsIgnoreCase(Config.char1)){     // 主要用于回复等操作
                        openid(serviceUUID,character.getUuid());
                    }else if(character.getUuid().toString().equalsIgnoreCase(Config.char3)){    // 主要用于实时数据、批量数据上传
                        openid(serviceUUID,character.getUuid());
                    }
                }
            }
        }
    }


    public void openid(UUID serviceUUID, UUID characterUUID) {
        //根据接收到的UUID，打开Indicate
        ClientManager.getClient().indicate(mMac,serviceUUID,characterUUID,bleNotifyResponse);
    }

    private BleNotifyResponse bleNotifyResponse = new BleNotifyResponse() {
        //在此函数中处理接收到蓝牙的数据，并且对数据进行解码，调用对用每个API的回调函数
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            String data = DataUtil.byteToHexString(value);
            if( data.length() <= 100){
                //Log.e("onNotify",character.toString()+"\n"+data);

                Log.e("onNotify", Tools.logbyte(value));
            }else {
                Log.e("chen", Tools.logbyte(value));
//                EventBus.getDefault().post(new DataBean(data));
            }

            if (datalenght == 0) {
                byte[] countl = {0x00, 0x00, value[3], value[2]};
                datalenght = TransUtils.Bytes2Dec(countl);
                databyte = value;
            }else{
                databyte = Tools.byteMerger(databyte,value);
            }

            if (datalenght==databyte.length){
                DataParser.newInstance().parseData(databyte);
                datalenght =0;
            }
        }

        @Override
        public void onResponse(int code) {
            Log.d("chen",code+"");
        }
    };


    IRequestResponse iRequestResponse = new IRequestResponse() {

        /**
         * 运动模式
         * @param result          0 成功       1 失败
         */
        @Override
        public void onsetSportTypeResponse(byte result){
            Log.e("dataRes","onsetSportTypeResponse "+result);
        }

        @Override
        public void onFirmWareUpdateResponse(byte result) {
            Log.e("result","onFirmWareUpdateResponse "+result);
        }

        @Override
        public void onDeleteDownloadedFirmWare(byte result) {
            Log.e("result","onDeleteDownloadedFirmWare "+result);
        }

        @Override
        public void onUpdateFWStatusResponse(byte result) {
            Log.e("result","onUpdateFWStatusResponse "+result);
        }

        @Override
        public void onFirmWareBlockResponse(byte result) {
            Log.e("result","onFirmWareBlockResponse "+result);
        }

        @Override
        public void onTimeSettingResponse(byte result) {
            Log.e("result","onTimeSettingResponse "+result);
        }

        @Override
        public void onAlarmSettingResponse(byte result) {
            Log.e("result","onAlarmSettingResponse "+result);
        }

        @Override
        public void onDeleteAlarmSetting(byte result) {
            Log.e("result","onDeleteAlarmSetting "+result);
        }

        @Override
        public void onModifyAlarmResponse(byte result) {
            Log.e("result","onModifyAlarmResponse "+result);
        }

        @Override
        public void onTargetSettingResponse(byte result) {
            Log.e("result","onTargetSettingResponse "+result);
        }

        @Override
        public void onUserInfoSettingResponse(byte result) {
            Log.e("result","onUserInfoSettingResponse "+result);
        }

        @Override
        public void onUnitSettingResponse(byte result) {
            Log.e("result","onUnitSettingResponse "+result);
        }

        @Override
        public void onLongsitSettingResponse(byte result) {
            Log.e("result","onLongsitSettingResponse "+result);
        }

        @Override
        public void onPreventLostOnOffResponse(byte result) {
            Log.e("result","onPreventLostOnOffResponse "+result);
        }

        @Override
        public void onPreventLostParamSettingResponse(byte result) {
            Log.e("result","onPreventLostParamSettingResponse "+result);
        }

        @Override
        public void onLeftOrRightHandSettingResponse(byte result) {
            Log.e("result","onLeftOrRightHandSettingResponse "+result);
        }

        @Override
        public void onMobileOSSettingResponse(byte result) {
            Log.e("result","onMobileOSSettingResponse "+result);
        }

        @Override
        public void onNotificationSettingResponse(byte result) {
            Log.e("result","onNotificationSettingResponse "+result);
        }

        @Override
        public void onHeartRateAlarmSettingResponse(byte result) {
            Log.e("result","onHeartRateAlarmSettingResponse "+result);
        }

        @Override
        public void onHeartRateMonitorResponse(byte result) {
            Log.e("result","onHeartRateMonitorResponse "+result);
        }

        @Override
        public void onFindMobileOnOffResponse(byte result) {
            Log.e("result","onFindMobileOnOffResponse "+result);
        }

        @Override
        public void onRecoverToDefaultSettingResponse(byte result) {
            Log.e("result","onRecoverToDefaultSettingResponse "+result);
        }

        @Override
        public void onDisturbeSettingResponse(byte result) {
            Log.e("result","onDisturbeSettingResponse "+result);
        }

        @Override
        public void onAerobicExerciseResponse(byte result) {
            Log.e("result","onAerobicExerciseResponse "+result);
        }

        @Override
        public void onLanguageSettingResponse(byte result) {
            Log.e("result","onLanguageSettingResponse "+result);
        }

        @Override
        public void onLeftTheWristToBrightResponse(byte result) {
            Log.e("result","onLeftTheWristToBrightResponse "+result);
        }

        @Override
        public void onBrightnessSettingResponse(byte result) {
            Log.e("result","onBrightnessSettingResponse "+result);
        }

        @Override
        public void onFindBandResponse(byte result) {
            Log.e("result","onFindBandResponse "+result);
        }

        @Override
        public void onHRMeasurementOnoffControl(byte result) {
            Log.e("result","onHRMeasurementOnoffControl "+result);
        }

        @Override
        public void onBPMeasurementOnoffControl(byte result) {
            Log.e("result","onBPMeasurementOnoffControl "+result);
        }


        @Override
        public void onBloodPressureCalibration(byte result) {
            Log.e("result","onBloodPressureCalibration "+result);
        }

        @Override
        public void onAppExitResponse(byte result) {
            Log.e("result","onAppExitResponse "+result);
        }

        @Override
        public void onAerobicExerciseOnOffResponse(byte result) {
            Log.e("result","onAerobicExerciseOnOffResponse "+result);
        }

        @Override
        public void onBindDeviceResponse(byte result) {
            Log.e("result","onBindDeviceResponse "+result);
        }

        @Override
        public void onUnBindDeviceResponse(byte result) {
            Log.e("result","onUnBindDeviceResponse "+result);
        }

        @Override
        public void onMessageNotificationResponse(byte result) {
            Log.e("result","onMessageNotificationResponse "+result);
        }

        @Override
        public void onRealTimeDataResponse(byte result) {
            Log.e("result","onRealTimeDataResponse "+result);
        }

        @Override
        public void onWaveFormPostResponse(byte result) {
            Log.e("result","onWaveFormPostResponse "+result);
        }

        @Override
        public void onFindPhoneResponse(byte result) {
            Log.e("result","onFindPhoneResponse "+result);
        }

        @Override
        public void onPreventLostResponse(byte result) {
            Log.e("result","onPreventLostResponse "+result);
        }

        @Override
        public void onAnswerOrRejectPhoneResponse(byte result) {
            Log.e("result","onAnswerOrRejectPhoneResponse "+result);
        }

        @Override
        public void onControlTheCamera(byte result) {
            Log.e("result","onControlTheCamera "+result);
        }

        @Override
        public void onControlTheMusic(byte result) {
            Log.e("result","onControlTheMusic "+result);
        }

        @Override
        public void onSynchronizdAllSwitchResponse(byte result) {
            Log.e("result","onSynchronizdAllSwitchResponse "+result);
        }

        @Override
        public void onBlockConfirmResponse(byte result) {
            Log.e("result","onBlockConfirmResponse "+result);
        }

        @Override
        public void onDeleteSportData(byte result) {
            Log.e("result","onDeleteSportData "+result);
        }

        @Override
        public void onDeleteSleepData(byte result) {
            Log.e("result","onDeleteSleepData "+result);
        }

        @Override
        public void onDeleteHeartRateData(byte result) {
            Log.e("result","onDeleteHeartRateData "+result);
        }

        @Override
        public void onDeleteBloodPressureData(byte result) {
            Log.e("result","onDeleteBloodPressureData "+result);
        }

        @Override
        public void onTestBlood(byte result) {
            Log.e("result","onTestBlood "+result);
            //write(ProtocolWriter.writeForUpdateWaveData());
        }

        @Override
        public void onUpdateWaveData(byte result) {
            Log.e("result","onUpdateWaveData "+result);
//            try {
//                Thread.sleep(100);
//                write(ProtocolWriter.writeForSamplingRate());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onSamplingRate(byte result1,byte result2) {
            Log.e("result","onSamplingRate "+result1+":"+result2);
        }

        @Override
        public void onUpdateOptoelectronicWaveform(byte[] result) {
            perGcdMsg(result);
//            Log.e("result","onUpdateOptoelectronicWaveform "+result);
        }


        /**
         * ppg
         * @param result          0 成功       1 失败
         */
        @Override
        public void onsetPPGHZResponse(byte result){
//            write(ProtocolWriter.writeForWaveUploadControl((byte)1,(byte)0));
        }

        @Override
        public void onSetSkinColor(byte result) {
            Log.e("result","onSetSkinColor:"+result);
        }

        @Override
        public void onBloodOxygenMeasure(byte result) {
            Log.e("dataRes","result:"+result);
        }

        @Override
        public void onRespiratoryRateMeasure(byte result) {
            Log.e("dataRes","result:"+result);
        }

        @Override
        public void onWeatherResponse(byte result) {
            Log.e("dataRes","result:"+result);
        }

        @Override
        public void onBloodPressureResponse(byte result) {
            Log.e("dataRes","result:"+result);
        }
    };

    public void perGcdMsg(byte[] readData){

        int lenght = readData.length - 6;

        int headCount = 4;

        int allCount = lenght/3;

        for (int i= 0; i<allCount ;i++) {

            int val = 0;
            int newHex = (readData[headCount+2+i*3] & 0xff);
            String newHexStr = Integer.toBinaryString(newHex);

            int index = 8 - newHexStr.length();
            for (int x=0;x<index;x++){
                newHexStr = "0"+newHexStr;
            }
//            System.out.println("xxxx="+newHexStr);
            String erStr = "0";
            if (newHexStr.length() == 8) {
                erStr.substring(0,1);
            }

            if (erStr.equals("1")) {
                byte[] bval = { (byte) 0xff ,readData[headCount+i*3+2],readData[headCount+i*3+1],readData[headCount+i*3]};
                val = TransUtils.Bytes2Dec(bval);
            }else{
                byte[] bval = { (byte) 0x00 ,readData[headCount+i*3+2],readData[headCount+i*3+1],readData[headCount+i*3]};
                val = TransUtils.Bytes2Dec(bval);
            }

            Log.e("result","onDeleteBloodPressureData :"+val);
        }
    }

    IDataResponse iDataResponse = new IDataResponse() {
        @Override
        public void onFirmWareInfoResponse(byte argument, int id, byte status, byte electricity, byte crfwsubversion, byte crfwmainversion, byte dldfwsubversion, byte dlfwmainversion, int dlbytes) {
            Log.e("dataRes","onFirmWareInfoResponse : argument:"+argument+" id:"+id+" electricity: "+electricity+
                    " crfwsubversion: "+crfwsubversion+" crfwmainversion: "+crfwmainversion+" dldfwsubversion: "+dldfwsubversion+" dlfwmainversion: "+dlfwmainversion
            +" dlbytes: "+dlbytes);
        }

        @Override
        public void onQueryAlarmClock(byte supportAlarmNum, byte settingAlarmNum, List<ClockInfo> alarmSet) {
            Log.e("dataRes","onQueryAlarmClock : "+"supportAlarmNum : "+supportAlarmNum+" settingAlarmNum : "+settingAlarmNum

            +"alarmSet:"+alarmSet.get(0).toString());
        }

        @Override
        public void onDeviceBaseInfo(int deviceId, int subVersion, int mainVersion, byte electricityStatus, byte electricity, byte bindStatus, byte synchronizedFlag) {
            Log.e("dataRes","onDeviceBaseInfo :  deviceId : "+deviceId+" subVersion : "+subVersion+" mainVersion : "+mainVersion+" electricityStatus : "+electricityStatus
            +" electricity : "+electricity+" bindStatus: "+bindStatus+" synchronizedFlag: "+synchronizedFlag);
        }

        @Override
        public void onDeviceSupportFunction(byte function1, byte function2, byte alarmNum, byte alarmType, byte messageNotify1, byte messageNotify2, byte otherFunction) {

        }

        @Override
        public void onDeviceMac(String mac) {

            Log.e("dataRes","onDeviceMac : "+mac);
        }

        @Override
        public void onDeviceName(String deviceName) {
            Log.e("dataRes","onDeviceName : "+deviceName);
        }

        @Override
        public void onCurrentHR(byte status, int hr) {
            Log.e("dataRes","onCurrentHR : status : "+status+" hr : "+hr);
        }

        @Override
        public void onCurrentBP(byte status, int systolic, int diastolic) {
            Log.e("dataRes","onCurrentBP : status : "+status+" systolic : "+systolic+" diastolic : "+diastolic);
        }

        @Override
        public void onQuerySamplingFreqResponse(int frequency) {
            Log.e("dataRes","onQuerySamplingFreqResponse : "+frequency);
        }

        @Override
        public void onSynchronizedTodaySport(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedTodaySport   recordNum:"+recordNum+" packDataLength:"+packDataLength+" allDataLength: "+allDataLength);
        }

        @Override
        public void onTodaySport(List<SportInfo> mlist) {
            for (SportInfo sportInfo : mlist) {
                Log.e("dataRes", "onTodaySport " + sportInfo.toString());
            }
        }

        @Override
        public void onSynchronizedHistorySport(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedHistorySport  recordNum : "+recordNum+" packDataLength : "+packDataLength
            +" allDataLength : "+allDataLength);
        }

        @Override
        public void onHistorySport(List<SportInfo> mlist) {
            for (SportInfo sportInfo : mlist) {
                Log.e("dataRes", "onHistorySport " + sportInfo.toString());
            }
        }

        @Override
        public void onSynchronizedTodaySleep(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedTodaySleep recordNum :"+recordNum+" packDataLength : "+packDataLength
            +" allDataLength: "+allDataLength);
        }

        @Override
        public void onTodaySleep(SleepInfo sleepInfo) {
            Log.e("dataRes","onTodaySleep "+sleepInfo.toString());
        }

        @Override
        public void onSynchronizedHistorySleep(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedHistorySleep recordNum : "+recordNum+" packDataLength : "+packDataLength+" allDataLength: "+allDataLength);
        }

        @Override
        public void onHistorySleep(List<SleepInfo> mlist) {
            for (SleepInfo sleepInfo : mlist) {
                Log.e("dataRes", "onHistorySleep " + sleepInfo.toString());
                Toast.makeText(MyBleService.getInstance(),sleepInfo.toString(), Toast.LENGTH_SHORT).show();
            }
            Log.e("dataRes", "onHistorySleep " + mlist.size());
        }

        @Override
        public void onSynchronizedTodayHeartRate(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedTodayHeartRate recordNum:"+recordNum+" packDataLength : "+packDataLength+" allDataLength : "+allDataLength);
        }

        @Override
        public void onTodayHeartRate(List<HeartInfo> mlist) {
            for (HeartInfo heartInfo : mlist) {
                Log.e("dataRes", "onTodayHeartRate " + heartInfo.toString());
            }
        }

        @Override
        public void onSynchronizedHistoryHeartRate(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedHistoryHeartRate recordNum : "+recordNum+" packDataLength : "+packDataLength+" allDataLength : "+allDataLength);
        }

        @Override
        public void onHistoryHeartRate(List<HeartInfo> mlist) {
            for (HeartInfo heartInfo : mlist) {
                Log.e("dataRes", "onHistoryHeartRate " + heartInfo.toString());
            }
        }

        @Override
        public void onSynchronizedTodayBloodPressure(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedTodayBloodPressure recordNum : "+recordNum+" packDataLength : "+packDataLength+" allDataLength : "+allDataLength);
        }

        @Override
        public void onTodayBloodPressure(List<BloodInfo> mlist) {
            for (BloodInfo bloodInfo : mlist) {
                Log.e("dataRes", "onTodayBloodPressure " + bloodInfo.toString());
            }
        }

        @Override
        public void onSynchronizedHistoryBloodPressure(int recordNum, int packDataLength, int allDataLength) {
            Log.e("dataRes","onSynchronizedHistoryBloodPressure recordNum : "+recordNum+" packDataLength : "+packDataLength+" allDataLength : "+allDataLength);
        }

        @Override
        public void onHistoryBloodPressure(List<BloodInfo> mlist) {
            for (BloodInfo bloodInfo : mlist){
                Log.e("dataRes", "onHistoryBloodPressure " + bloodInfo.toString());
            }
        }

        @Override
        public void onRealTimeSportData(int steps, int distance, int calorie) {
            Log.e("dataRes","onRealTimeSportData steps : "+steps+" distance : "+distance+" calorie : "+calorie);
        }

        @Override
        public void onRealTimeHeartRate(int heartRate) {
            Log.e("心率回调","onRealTimeHeartRate heartRate : "+heartRate);
        }

        @Override
        public void onRealTimeOxygen(int oxygen) {
            Log.e("dataRes","onRealTimeOxygen oxygen : "+oxygen);
        }

        @Override
        public void onRealTimeBloodPressure(int systolic, int diastolic, int heartRate) {
            Log.e("dataRes","onRealTimeBloodPressure systolic : "+systolic+" diastolic : "+diastolic+" heartRate : "+heartRate);
        }


        @Override
        public void onElectrocardiogram(List<Integer> Ecg_val) {
            Log.e("dataRes","Ecg_val"+Ecg_val);
//            Log.e("dataRes","onElectrocardiogram"+DataUtil.byteToHexString(electrocardiogram).substring(0,50));
        }

        @Override
        public void onOptoelectronic(byte[] optoelectronic) {
            Log.e("dataRes","onOptoelectronic "+ DataUtil.byteToHexString(optoelectronic).substring(0,50));
        }

        @Override
        public void onSportMode(int steps, int instance, int kacl, int sportTime) {
            Log.e("dataRes","steps:"+steps+",instance"+instance+",kacl"+kacl+",sportTime"+sportTime);
        }


    };


    IErrorCommand iErrorCommand = new IErrorCommand() {
        @Override
        public void onErrorCommand(String commandIdAndKey, int errorType) {
            Log.e("errorCommand","commandIdAndKey : "+commandIdAndKey+" errorType : "+errorType);
        }
    };

    IOperation iOperation = new IOperation() {
        @Override
        public void onDoSynchronizedHistorySport() {
            byte[] smsg = {0x05, 0x02, 0x01};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDoSynchronizedHistorySleep() {
            byte[] smsg = {0x05, 0x04, 0x01};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDoSynchronizedHistoryHeartRate() {
            byte[] smsg = {0x05, 0x06, 0x01};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDoSynchronizedHistoryBloodPressure() {
            byte[] smsg = {0x05, 0x08, 0x01};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDeleteSport() {
            byte[] smsg = {0x05, 0x40, 0x02};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDeleteSleep() {
            byte[] smsg = {0x05, 0x41, 0x02};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDeleteHeartRate() {
            byte[] smsg = {0x05, 0x42, 0x02};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDeleteBloodPressure() {
            byte[] smsg = {0x05, 0x43, 0x02};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }

        @Override
        public void onDoReceiveAllComplete() {
            byte[] smsg = {0x05, (byte) 0x80, 0x01};
            smsg = Tools.makeSend(smsg);
            write(smsg);
        }
    };

    /**
     *  写数据
     * @param data      写入蓝牙的数据
     */
    public void write(byte[] data){
        Log.d("chen888", Tools.logbyte(data));
        ClientManager.getClient().write(mMac,serviceUUID, UUID.fromString(Config.char1),data,writeResponse);
    }

    private BleWriteResponse writeResponse = new BleWriteResponse() {
        @Override
        public void onResponse(int code) {
            Log.e("writeResp","code = "+code);
        }
    };

    public void writeWithoutResponse(byte[] data){
        ClientManager.getClient().writeNoRsp(mMac,serviceUUID, UUID.fromString(Config.char2),data,writeResponse);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void updateblueToothState(Button button){
        if(isConnected == true){
            button.setText("已连接");
        }else{
            button.setText("未连接");
        }
    }
}
