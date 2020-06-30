package com.example.zhongyitizhi1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText mName;                        //邮箱编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //通过指定名称打开dbhelper
        dbHelper = new MyDatabaseHelper(this, "mydb.db3",1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        mName = (EditText) findViewById(R.id.register_name);//用户姓名
        mPwd = (EditText) findViewById(R.id.register_pwd);//密码
        mPwdCheck = (EditText) findViewById(R.id.register_checkpwd);//确认密码
        mSureButton = (Button) findViewById(R.id.register_btn_register);//注册
        mCancelButton = (Button) findViewById(R.id.register_btn_cancel); //返回
        mSureButton.setOnClickListener(m_register_Listener);      //注册界面两个按钮的监听事件
        mCancelButton.setOnClickListener(m_register_Listener);

    }
    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_register:                       //确认按钮的监听事件
                    register_check();
                    break;
                case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
            }
        }
    };
    public void register_check() {
        //确认按钮的监听事件，如果填写没有问题，则创建线程处理注册事件
        if (isUserNameAndPwdValid()) {
            new Thread(new Runnable() {
                @Override
                public void run() {//注册按钮监听事件
                    //设定好url
                    String url = HttpUtilsHttpURLConnection.BASE_URL + "/Register";
                    String userName = mName.getText().toString().trim();    //获取当前输入的用户名和密码信息
                    String userPwd = mPwd.getText().toString().trim();
                    Map<String, String> params = new HashMap<String, String>();
                    //向map赋值
                    params.put("name", userName);
                    params.put("password", userPwd);
                    //向servlet提交注册请求，传输注册姓名和密码
                    String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                    //用于调试确认结果
                    if (result != null) {
                        System.out.println("结果是" + result);
                    }
                    //向handler发送信息
                    Message msg = new Message();
                    msg.what = 0x12;
                    Bundle data = new Bundle();
                    //传输用户名，密码，结果
                    data.putString("result", result);
                    data.putString("name",userName);
                    data.putString("password",userPwd);
                    msg.setData(data);
                    hander.sendMessage(msg);
                }

                Handler hander = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 0x12) {
                            Bundle data = msg.getData();
                            //接受传输的message中的data
                            String key = data.getString("result");//得到json返回的json
                            String name1 = data.getString("name");
                            String password1=data.getString("password");
                            //  Toast.makeText(MainActivity.this,key,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject json = new JSONObject(key);
                                //从key中在获取服务器传回来的结果
                                String result = (String) json.get("result");
                                //如果注册成功了，做两件事：1。把用户名和密码登录到本地sqlite中，跳转到login
                                if ("success".equals(result)) {
                                    ContentValues values = new ContentValues();
                                    values.put("username",name1);
                                    values.put("password",password1);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    db.insert("user",null,values);
                                    System.out.println("插入用户成功");
                                    Intent intent = new Intent(Register.this,Login.class) ;    //切换Login Activity至User Activity
                                    startActivity(intent);
                                    finish();
                                } else if ("error".equals(result)) {
                                    Toast.makeText(Register.this, "用户名已存在！", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    System.out.println("这里出错了");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }).start();
        }
    }
    public boolean isUserNameAndPwdValid() {
        if (mName.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.email_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(!mPwd.getText().toString().trim().equals(mPwdCheck.getText().toString().trim())){
            Toast.makeText(this, getString(R.string.pwd_check_notright),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
