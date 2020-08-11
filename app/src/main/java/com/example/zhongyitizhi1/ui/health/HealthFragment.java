package com.example.zhongyitizhi1.ui.health;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.zhongyitizhi1.R;

import com.example.zhongyitizhi1.bluetooth.ClientManager;
import com.example.zhongyitizhi1.bluetooth.MyBleService;
import com.example.zhongyitizhi1.bluetooth.PermissionActivity;
import com.example.zhongyitizhi1.lishiqushi;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;

import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

public class HealthFragment extends Fragment{
    private MyBleService mMyBleService = MyBleService.getInstance();
    private HealthViewModel healthViewModel;
    public static DrawerLayout drawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    private Button mBlueToothConnection;
    private String mBlueToothMac = "";
    private static final int REQUEST_CODE_MAC = 0;
    private Timer mTimer = null;//计时器
    private TimerTask mTimerTask = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //获取healthviewmodel对象，估计这个of就把该viewmodel和该fragment关联起来了
        healthViewModel =
                ViewModelProviders.of(this).get(HealthViewModel.class);
        //装载xml布局，最后一个参数的意思是仅作用于子类而不作用于父类
        View root = inflater.inflate(R.layout.fragment_health, container, false);
        View view1 = root.findViewById(R.id.chakanqushi);
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), lishiqushi.class);
                startActivity(intent1);
            }
        });
        mBlueToothConnection = root.findViewById(R.id.bluetoothconnection);
        mBlueToothConnection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = PermissionActivity.newIntent(getActivity(),mBlueToothMac);
                startActivityForResult(intent1,REQUEST_CODE_MAC);
            }
        });
        startTime();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("99999998");
//            }
//        };
//        timer.schedu,
//                1000,//延迟1秒执行
//                Time);//周期时间
//
//    }

        //定义一个textview，然后将healthviewmodel读取他的text然后监听其是否改变，如果改变则将textview设为string s
//        final TextView textView = root.findViewById(R.id.text_health);
//        healthViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        //设置按钮
//        final Button button = root.findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Toast.makeText(getActivity(), "hello world 2", Toast.LENGTH_SHORT).show();
//            }
//        });
        //返回的 View 必须是片段布局的根视图(返回一个view，该view就是该fragment显示的view)
        return root;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateBlueToothConnectedState();
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_MAC){
            if(data == null){
                return;
            }
            mBlueToothMac = PermissionActivity.getConnectedMac(data);
            if(mBlueToothMac != ""){
//                ClientManager.getClient().registerConnectStatusListener(mBlueToothMac, mBleConnectStatusListener);
            }

        }
    }
    private void updateBlueToothConnectedState(){
        if (mMyBleService.isConnected) {
            mBlueToothConnection.setText("已连接");
        } else{
            mBlueToothConnection.setText("未连接");
        }
    }
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            updateBlueToothConnectedState();
        };
    };
    /**
     * 开始自动减时
     */
    private void startTime() {
        if(mTimer==null){
            mTimer = new Timer();
        }
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                mHandler.sendMessage(message);//发送消息
            }
        };
        mTimer.schedule(mTimerTask, 1000);//1000ms执行一次
    }
    /**
     * 停止自动减时
     */
    private void stopTime() {
        if(mTimer!=null)
            mTimer.cancel();
    }

}
