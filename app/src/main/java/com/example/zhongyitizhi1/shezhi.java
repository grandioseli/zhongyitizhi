package com.example.zhongyitizhi1;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zhongyitizhi1.R;

public class shezhi extends AppCompatActivity {
    private Button outButton;
    private MyDatabaseHelper dbHelper;              //sqlite类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //常规
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);
        outButton = findViewById(R.id.tuichudenglu);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        dbHelper = new MyDatabaseHelper(this, "mydb.db3",1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        outButton.setOnClickListener(mListener);
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tuichudenglu:
                    ContentValues values = new ContentValues();
                    values.put("isautologin", 2);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.update("autologin", values, "number = ?",new String[]{"1"} );
                    System.out.println("已经退出登录" );//登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(shezhi.this,Login.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
            }
        }
    };
    //重写这个方法就可以实现toolbar的返回，目前尚未看懂
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}