package com.example.zhongyitizhi1;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zhongyitizhi1.R;

public class shezhi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //常规
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }
    //重写这个方法就可以实现toolbar的返回，目前尚未看懂
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}