package com.example.zhongyitizhi1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {                 //登录界面活动
    private TextView forgetpwd;
    private EditText mName;                          //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮
    private CheckBox misautologin;                  //自动登录选项
    private MyDatabaseHelper dbHelper;              //sqlite类
    private int autologin;                             //自动登录的标志
    private String username;                            //存储最近一次登录用户的姓名

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_in);
        //创建datahelper类
        dbHelper = new MyDatabaseHelper(this, "mydb.db3",1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询自动登录标志：如果有数据，判断是否是1如果是1则直接跳转
        Cursor cursor = db.query("autologin",null,null,null,null,null,null);
        if(cursor !=null&&cursor.getCount()>0)
        {
            System.out.println("有数据");
            if(cursor.moveToFirst())
            {
                autologin=cursor.getInt(1);
                System.out.println("当前数据是" + autologin);
                if(autologin==1)
                {
                    Intent intent = new Intent(Login.this,MainActivity.class) ;    //切换Login Activity至User Activity
                    startActivity(intent);
                    finish();
                }
            }
        }
        //否则就说明没有数据，插入数据并设为2，也就是不跳转
        else
        {
            System.out.println("无数据插入数据");
            ContentValues values = new ContentValues();
            values.put("number",1);
            values.put("isautologin",2);
            db.insert("autologin",null,values);
            System.out.println("当前数据是" + autologin);
        }
        //通过id找到相应的控件
        forgetpwd = (TextView) findViewById(R.id.forget_password);
        mName = (EditText) findViewById(R.id.login_name);
        mPwd = (EditText) findViewById(R.id.login_pwd);
        misautologin = (CheckBox) findViewById(R.id.isautologin);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_signin);
        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);
        forgetpwd.setOnClickListener(mListener);
        //此处设置自动登录
    }
    OnClickListener mListener = new OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:                            //登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login_btn_signin:                              //登录界面的登录按钮
                    login();
                    break;
                case R.id.forget_password:
                    Intent intent_Login_to_Forgetpwd = new Intent(Login.this,Forgetpwd.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_Forgetpwd);
                    finish();
                    break;
            }
        }
    };
    public void login() {
                if (isUserNameAndPwdValid()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {//登录按钮监听事件
                            //设定好url，读取输入的用户名密码，传给服务器做验证
                            String url = HttpUtilsHttpURLConnection.BASE_URL + "/Login";
                            String userName = mName.getText().toString().trim();    //获取当前输入的用户名和密码信息
                            String userPwd = mPwd.getText().toString().trim();
                            Map<String, String> params = new HashMap<String, String>();
                            //向map赋值
                            params.put("name", userName);
                            params.put("password", userPwd);
                            String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                            if (result != null) {
                                System.out.println("结果是" + result);
                            }
                            Message msg = new Message();
                            msg.what = 0x12;
                            Bundle data = new Bundle();
                            //向data中传入结果和用户名
                            data.putString("username",userName);
                            data.putString("password",userPwd);
                            //这里把result传入，result包含是否成功的标志，用户第一次登录，用户的信息
                            data.putString("result", result);
                            msg.setData(data);
                            hander.sendMessage(msg);
                        }

             Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x12) {
                        Bundle data = msg.getData();
                        System.out.println("data的数据是"+data);
                        String lastusername = data.getString("username");
                        String lastpassword = data.getString("password");
                        String key = data.getString("result");//得到json返回的json
                        //  Toast.makeText(MainActivity.this,key,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject json = new JSONObject(key);
                            String result = (String) json.get("result");
                            int firstlogin =(int) json.get("firstlogin");
                            String phone = (String)json.get("phone");
                            int gender = (int)json.get("gender");
                            int physique = (int)json.get("physique");
                            //如果返回的结果是"success"，说明系统可以跳转了，那么接下来要做这几个工作：1.记录最近一次登录的用户，若非登出，每次界面中的数据都将是这个用户
                            //的数据。2如果用户点击了自动登录，在sqlite中将标志设为1，下次登陆界面如果查看到标志为1则会自动跳转。
                            if ("success".equals(result)) {
                                //用于记录当前登录的用户信息
                                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                                Cursor cursor = db2.query("userin",null,null,null,null,null,null);
                                //如果游标不为0，说明已经曾经有用户登录成功过
                                if(cursor !=null&&cursor.getCount()>0)
                                {
                                    System.out.println("不是第一次登录了");
                                    if(cursor.moveToFirst())
                                    {
                                        username=cursor.getString(1);
                                        System.out.println("记录用户名是" +username );
                                        ContentValues values = new ContentValues();
                                        values.put("username",lastusername);
                                        db2.update("userin",values,"number = ?",new String[]{"1"});
                                        System.out.println("已经更新数据");
                                        System.out.println("更新后的用户是"+lastusername);
                                    }
                                }
                                else
                                {
                                    System.out.println("之前没有用户登录过");
                                    ContentValues values = new ContentValues();
                                    values.put("number",1);
                                    values.put("username",lastusername);
                                    db2.insert("userin",null,values);
                                    System.out.println("插入完成" );
                                }
                                //向sqlite的user表中插入当前用户信息
                                db2.execSQL("replace into user(username,password,user_phone,gender,physique) values(?,?,?,?,?)",new Object[]{lastusername,lastpassword,phone,gender,physique});
                                System.out.println("已经插入或者更新完毕用户信息");
                                Cursor cursor3 = db2.query("user",null,null,null,null,null,null);
                                if(cursor3 !=null&&cursor3.getCount()>0)
                                {
                                    System.out.println("用户表里有数据");
                                    while(cursor3.moveToNext())
                                    {
                                        String un = cursor3.getString(0);
                                        String pw = cursor3.getString(1);
                                        String ph = cursor3.getString(2);
                                        int gd = cursor3.getInt(3);
                                        int py = cursor3.getInt(4);
                                        System.out.println("用户名为" + un);
                                        System.out.println("密码为"+pw);
                                        System.out.println("电话为" + ph);
                                        System.out.println("性别为" + gd);
                                        System.out.println("体质为"+ py);
                                    }
                                }
                                System.out.println("int当前数据是" + firstlogin);
                                //如果点击了自动登录，那么就在sqlite中将相应的标志设为1，下次登录时候检查标志是否为1，为1的话自动跳转
                                if(misautologin.isChecked())
                                {
                                    ContentValues values = new ContentValues();
                                    values.put("isautologin", 1);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    db.update("autologin", values, "number = ?",new String[]{"1"} );
                                    System.out.println("当前数据是" + autologin);
                                }
                                //如果是第一次登录，就跳转到完善信息
                                if(firstlogin==0)
                                {
                                    Intent intent = new Intent(Login.this,fixInfo.class) ;    //切换Login Activity至User Activity
                                    startActivity(intent);
                                    finish();
                                }
                                //如果不是第一次登录就直接跳转到主界面
                                else
                                    {
                                    Intent intent = new Intent(Login.this,MainActivity.class) ;    //切换Login Activity至User Activity
                                    startActivity(intent);
                                    finish();
                                }
                            } else if ("error".equals(result)) {
                                Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                            }
                            else{
                                System.out.println("出现了预料之外的错误");
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
        }
        return true;
    }
}
