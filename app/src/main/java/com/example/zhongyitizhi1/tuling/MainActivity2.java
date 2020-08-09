package com.example.zhongyitizhi1.tuling;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.zhongyitizhi1.R;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.sunfusheng.FirUpdater;

public class MainActivity2 extends BaseActivity {

    //相当于findviewbyid
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_message)
    ListView lvMessage;
    @BindView(R.id.iv_send_msg)
    ImageView ivSendMsg;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.rl_msg)
    RelativeLayout rlMsg;
    //创建arraylist
    private List<MessageEntity> msgList = new ArrayList<>();
    //创建chatadapter，该适配器是继承与baseadapter
    private ChatMessageAdapter msgAdapter;
    //创建gson实例
    private Gson mGson;
    //这里是需要动态申请的权限
    //回调标志
    private static final int AUDIO_PERMISSIONS_CODE = 1;
    private static final String[] AUDIO_PERMISSIONS = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final String mNewsText = "你大爷的";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //写了这个就不用再写findviewbyid
        ButterKnife.bind(this);
        //似乎是用于检查app更新信息的
        // new FirUpdater(this, "3c57fb226edf7facf821501e4eba08d2", "5704953c00fc74127000000a").checkVersion();
//初始化listview
        initData();
        SpeechUtility.createUtility(MainActivity2.this, SpeechConstant.APPID + "=5f201d45");
        mGson = new Gson();
        requestPermission();
        //初始化activity的view，主要是设定toolbar，并加上标题
//        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        initListener();
    }

    private void initData() {
        //如果arraylist的长度为0，就添加新的消息实体，类型为左边
        if (msgList.size() == 0) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_LEFT, TimeUtil.getCurrentTimeMillis());
            entity.setText("你好！我是中医体质小助手！\n关于体质以及app的知识你可以来问我哦！\n你有什么要问的么？");
            msgList.add(entity);
        }
        //为listview绑定适配器
        msgAdapter = new ChatMessageAdapter(this, msgList);
        lvMessage.setAdapter(msgAdapter);
        lvMessage.setSelection(msgAdapter.getCount());
    }

    private void initView() {
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    //绑定监听器
    private void initListener() {
        ivSendMsg.setOnClickListener(v -> sendMessage());
//        ivSendMsg.setOnClickListener(v -> funcDemo());

        lvMessage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                KeyBoardUtil.hideKeyboard(mActivity);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //重写这个方法就可以实现toolbar的返回，目前尚未看懂
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent(MainActivity2.this, AboutActivity.class);
            startActivity(intent);
            System.out.println("关于");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.action_about:
//                NavigateManager.gotoAboutActivity(mContext);
//                return true;
//            default:
//                return false;
//        }
//    }

    // 给Turing发送问题
    public void sendMessage() {
        //获取用户输入的文本信息
        String msg = etMsg.getText().toString().trim();
        //如果文本不为空
        if (!IsNullOrEmpty.isEmpty(msg)) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(), msg);
            msgList.add(entity);
            //通知listview更新
            msgAdapter.notifyDataSetChanged();
            etMsg.setText("");

            // 仅使用 Retrofit 请求接口
//            requestApiByRetrofit(msg);

            // 使用 Retrofit 和 RxJava 请求接口
            requestApiByRetrofit_RxJava(msg);
        }
    }

    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit(String info) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TulingParams.TULING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);

        Call<MessageEntity> call = api.getTuringInfo(TulingParams.TULING_KEY, info);
        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                handleResponseMessage(response.body());
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {

            }
        });
    }
    private void requestPermission() {
        // 当API大于 23 （M表示23）时，才动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(MainActivity2.this,AUDIO_PERMISSIONS,AUDIO_PERMISSIONS_CODE);
        }
    }
    //处理权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AUDIO_PERMISSIONS_CODE:
                //权限请求失败
                if (grantResults.length == AUDIO_PERMISSIONS.length) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            //弹出对话框引导用户去设置
                            showDialog();
//                            Toast.makeText(MainActivity.this, "请求权限被拒绝", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }else{
                    Toast.makeText(MainActivity2.this, "已授权", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    //弹出提示框
    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("是否授予录音权限？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        goToAppSetting();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
    private void goToAppSetting(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(String info) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TulingParams.TULING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);

        api.getTuringInfoByRxJava(TulingParams.TULING_KEY, info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage, Throwable::printStackTrace);
    }

    // 处理获得到的问答信息
    private void handleResponseMessage(MessageEntity entity) {
        if (entity == null) return;

        entity.setTime(TimeUtil.getCurrentTimeMillis());
        entity.setType(ChatMessageAdapter.TYPE_LEFT);

        switch (entity.getCode()) {
            case TulingParams.TulingCode.URL:
                entity.setText(entity.getText() + "，点击网址查看：" + entity.getUrl());
                break;
            case TulingParams.TulingCode.NEWS:
                entity.setText(entity.getText() + "，点击查看");
                break;
        }

        msgList.add(entity);
        msgAdapter.notifyDataSetChanged();
    }
//语音合成
public void onSynthesize(View view) {
    //1.创建 SpeechSynthesizer 对象, 第二个参数： 本地合成时传 InitListener
    SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(MainActivity2.this, null);
    //2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
    //设置发音人（更多在线发音人，用户可参见 附录13.2
    mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
    mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
    mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
    //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
    //仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
    //3.开始合成
    mTts.startSpeaking(mNewsText, null);
}
//语音识别
public void onRecognise(View view) {
    //1.创建RecognizerDialog对象
    RecognizerDialog mDialog = new RecognizerDialog(this, null);
    //2.设置accent、 language等参数
    mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
    mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
    //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
    //结果
//         mDialog.setParameter("asr_sch", "1");
//         mDialog.setParameter("nlp_version", "2.0");
    //3.设置回调接口
    mDialog.setListener(mRecognizerDialogListener);
    //4.显示dialog，接收语音输入
    mDialog.show();
}

    public RecognizerDialogListener mRecognizerDialogListener =  new RecognizerDialogListener() {
        /**
         *
         * @param recognizerResult 语音识别结果
         * @param b true表示是标点符号
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            // Toast.makeText(MainActivity.this, recognizerResult.getResultString(), Toast.LENGTH_LONG).show();
            if (b) {
                return;
            }
            ResultBean resultBean = mGson.fromJson(recognizerResult.getResultString(), ResultBean.class);
            List<ResultBean.WsBean> ws = resultBean.getWs();
            String w = "";
            for (int i = 0; i < ws.size(); i++) {
                List<ResultBean.WsBean.CwBean> cw = ws.get(i).getCw();
                for (int j = 0; j < cw.size(); j++) {
                    w += cw.get(j).getW();
                }
            }
            etMsg.setText(w);
//            Toast.makeText(MainActivity2.this, w, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };
}