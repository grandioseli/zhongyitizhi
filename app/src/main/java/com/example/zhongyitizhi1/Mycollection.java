package com.example.zhongyitizhi1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Mycollection extends AppCompatActivity {
    private Button findmore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //常规
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollection);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        Button findmore = findViewById(R.id.findmore);
        //绑定button函数
        findmore.setOnClickListener(mListener);
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }
    View.OnClickListener mListener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.findmore:                       //确认按钮的监听事件
                    Intent intent = new Intent(Mycollection.this,MainActivity.class) ;    //切换User Activity至Login Activity
                    intent.putExtra("bigMake",1);
                    startActivity(intent);
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
