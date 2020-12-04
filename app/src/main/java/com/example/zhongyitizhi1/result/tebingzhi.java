package com.example.zhongyitizhi1.result;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zhongyitizhi1.R;
import com.example.zhongyitizhi1.tizhi;

public class tebingzhi extends AppCompatActivity {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private int button = 1;
    private TextView tiaoliyuanze;
    private TextView tiaolineirong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //常规
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tebingzhi);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        b1 = findViewById(R.id.pinghezhi_button1);
        b2 = findViewById(R.id.pinghezhi_button2);
        b3 = findViewById(R.id.pinghezhi_button3);
        b4 = findViewById(R.id.pinghezhi_button4);
        b5 = findViewById(R.id.pinghezhi_button5);
        tiaoliyuanze = findViewById(R.id.tiaoliyuanze);
        tiaolineirong = findViewById(R.id.tiaolineirong);
        b1.setOnClickListener(mListener);
        b2.setOnClickListener(mListener);
        b3.setOnClickListener(mListener);
        b4.setOnClickListener(mListener);
        b5.setOnClickListener(mListener);
        b1.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
        b1.setTextColor(Color.parseColor("#ffffff"));
        View view1 = findViewById(R.id.submit);
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(tebingzhi.this, tizhi.class) ;    //切换Login Activity至User Activity
                startActivity(intent);
                finish();
            }
        });
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pinghezhi_button1:                            //登录界面的注册按钮
                    if (button != 1) {
                        changebutton(button);
                        b1.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b1.setTextColor(Color.parseColor("#ffffff"));
                        button = 1;
                        tiaoliyuanze.setText("调理原则:补气、温肺补中");
                        tiaolineirong.setText("有很多体寒的人，一年到头打喷嚏，流清鼻涕。其实根本原因是寒邪引发的肺气不降。需要首先用温通的方法将身体的经络打开，祛除积年的寒邪，让肺气得以正常肃降。除了平时要注意保暖，少食生冷食物以外。可以采用艾灸温经除寒。");
                    }
                    break;
                case R.id.pinghezhi_button2:                              //登录界面的登录按钮
                    if (button != 2) {
                        changebutton(button);
                        b2.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b2.setTextColor(Color.parseColor("#ffffff"));
                        button = 2;
                        tiaoliyuanze.setText("调理原则：补肺气，升阳气");
                        tiaolineirong.setText("“肺主气，司呼吸，外合皮毛，通调水道”。肺气虚，肺调节呼吸，抵御外邪的作用就会减弱，出现说话感觉上气不接下气、声音低沉、咳嗽气喘、胸闷的现象，而且特别容易感冒，尤其是在秋冬两季，感觉风一吹就会生病。");
                    }
                    break;
                case R.id.pinghezhi_button3:                            //登录界面的注册按钮
                    if (button != 3) {
                        changebutton(button);
                        b3.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b3.setTextColor(Color.parseColor("#ffffff"));
                        button = 3;
                        tiaoliyuanze.setText("补气，增强免疫力");
                        tiaolineirong.setText("荨麻疹俗称风团、风疹团，是一种常见的皮肤病，多因先天禀赋不足或长期不良生活习惯或请与抑郁导致的抵抗力下降造成，导致身体易感外邪，另某些食物、药物、疲劳、风、冷、湿等等都成为了诱发寻麻疹的因素。");
                    }
                    break;
                case R.id.pinghezhi_button4:                            //登录界面的注册按钮
                    if (button != 4) {
                        changebutton(button);
                        b4.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b4.setTextColor(Color.parseColor("#ffffff"));
                        button = 4;
                        tiaoliyuanze.setText("调理原则：增强免疫力");
                        tiaolineirong.setText("过敏性哮喘又称外源性哮喘，是过敏性疾病中最常见的一种病症。呼吸不畅，尤其在呼气时伴有高调呼吸声；气喘吁吁，呼吸的频率过快。药物、饮食、运动、天气变化肺部感染等等固然有一定的原因。但是提升自身的免疫能力更为重要。");
                    }
                    break;
            }
        }
    };
    public void changebutton(int i)
    {
        switch(i)
        {
            case 1:
                b1.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze);
                b1.setTextColor(Color.parseColor("#2ebf91"));
            case 2:
                b2.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze);
                b2.setTextColor(Color.parseColor("#2ebf91"));
            case 3:
                b3.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze);
                b3.setTextColor(Color.parseColor("#2ebf91"));
            case 4:
                b4.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze);
                b4.setTextColor(Color.parseColor("#2ebf91"));
        }
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
