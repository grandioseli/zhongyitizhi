package com.example.zhongyitizhi1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class fixInfo extends AppCompatActivity {

    private Button submitButton;
    private RadioButton b1;
    private RadioButton b2;
    private EditText phoneEdit;
    private MyDatabaseHelper dbHelper;
    private String username;//记录用户名
    private String phone;//记录手机号
    private int genderInt;//记录性别

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixinfo);
        //根据id查找组件
        b1 = (RadioButton) findViewById(R.id.radio1);
        b2 = (RadioButton) findViewById(R.id.radio2);
        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(mListener);
        phoneEdit = findViewById(R.id.edit_phone);
        //创建dbhelper
        dbHelper = new MyDatabaseHelper(this, "mydb.db3", 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("userin", null, null, null, null, null, null);
        //如果游标不为0，说明已经曾经有用户登录成功过
        if (cursor.moveToFirst())
        {
            System.out.println("读取到用户信息");
            username = cursor.getString(1);
            System.out.println("记录用户名是" + username);
        }
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submit:
                    //如果填写没问题
                    if (isGenderAndPhoneValid()) {
                        System.out.println("填写无误");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {//登录按钮监听事件
                                if (b1.isChecked()) {
                                    genderInt = 1;
                                    phone = phoneEdit.getText().toString().trim();
                                    //如果选择了男性，则想服务器发送一个"man"字符
                                    String url = HttpUtilsHttpURLConnection.BASE_URL + "/Complete";
                                    String gender = "man";    //如果点了男性，把gender设为man
                                    Map<String, String> params = new HashMap<String, String>();
                                    //向map赋值
                                    params.put("name", username);
                                    params.put("phone", phone);
                                    params.put("gender", gender);
                                    String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                                    if (result != null) {
                                        System.out.println("结果是" + result);
                                    }
                                } else if (b2.isChecked()) {
                                    //如果选择了女性，则想服务器发送一个"woman"字符
                                    genderInt = 2;
                                    String url = HttpUtilsHttpURLConnection.BASE_URL + "/Complete";
                                    phone = phoneEdit.getText().toString().trim();
                                    String gender = "woman";    //如果点了女性，把gender设为woman
                                    Map<String, String> params = new HashMap<String, String>();
                                    //向map赋值
                                    params.put("name", username);
                                    params.put("phone", phone);
                                    params.put("gender", gender);
                                    String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                                    if (result != null) {
                                        System.out.println("结果是" + result);
                                    }
                                }
                                System.out.println("将"+phone+"插入用户数据");
                                //向用户表中更新数据
                                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                                db2.execSQL("update user set user_phone =? , gender=? where username =?",new Object[]{phone,genderInt,username});
                                System.out.println("已经插入或者更新完毕用户信息");
                                Cursor cursor3 = db2.query("user",null,null,null,null,null,null);
                                if(cursor3 !=null&&cursor3.getCount()>0)
                                {
                                    System.out.println("用户表里有数据");
                                    while(cursor3.moveToNext())
                                    {
                                        String un = cursor3.getString(0);
                                        String pw = cursor3.getString(1);
                                        int ph = cursor3.getInt(2);
                                        int gd = cursor3.getInt(3);
                                        int py = cursor3.getInt(4);
                                        System.out.println("用户名为" + un);
                                        System.out.println("密码为"+pw);
                                        System.out.println("电话为" + ph);
                                        System.out.println("性别为"+gd);
                                        System.out.println("体质为"+py);
                                    }
                                }
                                Intent intent_Login_to_Register = new Intent(fixInfo.this, MainActivity.class);    //切换Login Activity至User Activity
                                startActivity(intent_Login_to_Register);
                                finish();
                            }
                        }).start();
                    }
                    break;
            }
        }
    };

    public boolean isGenderAndPhoneValid() {
        if (!b1.isChecked() && !b2.isChecked()) {
            Toasty.info(fixInfo.this, "您尚未选择您的性格", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (phoneEdit.getText().toString().trim().equals("")) {
            Toasty.info(fixInfo.this, "您尚未填写手机号", Toast.LENGTH_SHORT, true).show();
            return false;
        }
        else if(phoneEdit.getText().length()!=11)
        {
            Toasty.info(fixInfo.this,"您的手机号位数填写得不正确",Toasty.LENGTH_LONG,true).show();
            return false;
        }
        return true;
    }
}
