package com.example.zhongyitizhi1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import es.dmoral.toasty.Toasty;

public class Forgetpwd extends Activity {
    private Button verification;
    private Button return_to_login;
    private Button submit;
    private EditText phone;
    private EditText etVerification;
    private EditText newpassword;
    private EditText username;
    private String phoneNumber;         // 电话号码
    private String verificationCode;  //验证码
    private String newpassword1;    //新密码
    private String username1;       //用户名
    private boolean flag;   // 操作是否成功
    final Context context = Forgetpwd.this;                       // context
    final String AppKey = "2f0ec35793944";                       // AppKey
    final String AppSecret = "a2afe4151baec73ea863b9ac74250e21"; // AppSecret
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
        //绑定组件
        submit = (Button) findViewById(R.id.submit_change);
        newpassword=(EditText) findViewById(R.id.new_pwd);
        phone = (EditText) findViewById(R.id.phone);
        username = (EditText) findViewById(R.id.username);
        etVerification = (EditText) findViewById(R.id.edit_verification);
        return_to_login= (Button) findViewById(R.id.forgetpwd_btn_login);
        verification = (Button) findViewById(R.id.verification);
        //设定监听函数
        verification.setOnClickListener(mListener);
        return_to_login.setOnClickListener(mListener);
        submit.setOnClickListener(mListener);
        MobSDK.submitPolicyGrantResult(true, null);
        EventHandler eventHandler = new EventHandler(){       // 操作回调
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);     // 注册回调接口
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.verification:
                    if (!TextUtils.isEmpty(phone.getText())) {
                        if (phone.getText().length() == 11) {
                            phoneNumber = phone.getText().toString();
                            Toast.makeText(Forgetpwd.this, "开始请求发送验证短信", Toast.LENGTH_SHORT).show();
                            CountDownTimer timer = new CountDownTimer(60000, 1000) {//参数为 （倒计时长，间隔）
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    verification.setEnabled(false);
                                    verification.setText("已发送(" + millisUntilFinished / 1000 + ")");
                                }
                                @Override
                                public void onFinish() {
                                    verification.setEnabled(true);
                                    verification.setText("重新发送");
                                }
                            }.start();
                            SMSSDK.getVerificationCode("86", phoneNumber); // 发送验证码给号码的 phoneNumber 的手机
                            phone.requestFocus();
                            Toast.makeText(Forgetpwd.this, "已发送验证短信", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Forgetpwd.this, "请输入完整的电话号码", Toast.LENGTH_SHORT).show();
                            phone.requestFocus();
                        }
                    } else {
                        Toast.makeText(Forgetpwd.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                        phone.requestFocus();
                    }
                    break;
                case R.id.forgetpwd_btn_login:
                    Intent intent = new Intent(Forgetpwd.this,Login.class) ;    //切换Login Activity至User Activity
                    startActivity(intent);
                    finish();
                    break;
                case R.id.submit_change:
                    if (!TextUtils.isEmpty(etVerification.getText())) {
                        if (etVerification.getText().length() == 6) {
                            verificationCode = etVerification.getText().toString();
                            SMSSDK.submitVerificationCode("86", phoneNumber, verificationCode);
                            flag = false;
                        } else {
                            Toast.makeText(Forgetpwd.this, "请输入完整的验证码", Toast.LENGTH_SHORT).show();
                            etVerification.requestFocus();
                        }
                    } else {
                        Toast.makeText(Forgetpwd.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                        etVerification.requestFocus();
                    }
                    break;
            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 如果操作成功
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 校验验证码，返回校验的手机和国家代码
                    new Thread(new Runnable() {
                        @Override
                        public void run() {//登录按钮监听事件
                            phoneNumber = phone.getText().toString().trim();
                            username1 = username.getText().toString().trim();
                            newpassword1 = newpassword.getText().toString().trim();
                            String url = HttpUtilsHttpURLConnection.BASE_URL + "/ForgetPwd";
                            Map<String, String> params = new HashMap<String, String>();
                            //向map赋值
                            params.put("phone", phoneNumber);
                            params.put("username", username1);
                            params.put("newpassword", newpassword1);
                            //向servlet提交注册请求，传输注册姓名和密码
                            String result1 = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                            //用于调试确认结果
                            if (result1 != null) {
                                System.out.println("结果是" + result1);
                            }
                            Message msg1 = new Message();
                    msg1.what = 0x15;
                    Bundle data1 = new Bundle();
                            data1.putString("result", result1);
                    msg1.setData(data1);
                    handler1.sendMessage(msg1);
                        }
                    }).start();
//                    //向handler发送信息
//                    Message msg1 = new Message();
//                    msg1.what = 0x12;
//                    Bundle data1 = new Bundle();
//                    //传输用户名，密码，结果
//                    data1.putString("result", result1);
//                    data1.putString("name",username1);
//                    data1.putString("password",newpassword1);
//                    msg1.setData(data1);
//                    handler1.sendMessage(msg1);
//                    Intent intent = new Intent(Register.this, MainActivity.class);
//                    startActivity(intent);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                    // 获取验证码成功，true为智能验证，false为普通下发短信
                    Toast.makeText(Forgetpwd.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else {
                // 如果操作失败
                if (flag) {
                    Toast.makeText(Forgetpwd.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(Forgetpwd.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x15)
            {
                Bundle data = msg.getData();
                System.out.println("data的数据是"+data);
                String key = data.getString("result");//得到json返回的json
                try {
                    JSONObject json = new JSONObject(key);
                    String result = (String) json.get("result");
                    if ("success".equals(result)) {
                        Toasty.success(Forgetpwd.this, "重置成功，现在跳转到登录界面", Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(Forgetpwd.this,Login.class) ;    //切换Login Activity至User Activity
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toasty.warning(Forgetpwd.this, "操作失败，请检查您的填写信息是否正确", Toast.LENGTH_SHORT, true).show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();  // 注销回调接口
    }
    public boolean iseverythingok() {
        if (phone.getText().toString().trim().equals("")) {
            Toasty.info(Forgetpwd.this, "您尚未选择您的性格", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (newpassword.getText().toString().trim().equals("")) {
            Toasty.info(Forgetpwd.this, "您尚未填写您的新密码", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if(username.getText().toString().trim().equals(""))
        {
            Toasty.info(Forgetpwd.this, "您尚未填写您的账户名", Toast.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }
}
