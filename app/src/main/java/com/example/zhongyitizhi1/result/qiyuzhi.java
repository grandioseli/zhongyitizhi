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

public class qiyuzhi extends AppCompatActivity {
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
        setContentView(R.layout.qiyuzhi);
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
                Intent intent = new Intent(qiyuzhi.this, tizhi.class) ;    //切换Login Activity至User Activity
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
                        tiaoliyuanze.setText("调理原则：理气和胃，促进睡眠");
                        tiaolineirong.setText("焦虑的人，由于情志不调，压力大等原因导致气机不同，五脏六腑的机能紊乱了，肝气郁结，胃气上逆，经常出现睡眠问题。「胃不和则卧不安」所以要调理焦虑问题，首先要调理脾胃，疏肝理气，保障睡眠质量才可以。");
                    }
                    break;
                case R.id.pinghezhi_button2:                              //登录界面的登录按钮
                    if (button != 2) {
                        changebutton(button);
                        b2.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b2.setTextColor(Color.parseColor("#ffffff"));
                        button = 2;
                        tiaoliyuanze.setText("调理原则：疏肝，降火");
                        tiaolineirong.setText("郁为结聚而不得发越之谓。当升不升，当降不降，气郁体质的人，气机运行不畅，心情也不好。经常容易生气，且脾气暴躁。但是发完脾气之后又会觉得有些后悔。这些问题都应该从疏肝理气着手。");
                    }
                    break;
                case R.id.pinghezhi_button3:                            //登录界面的注册按钮
                    if (button != 3) {
                        changebutton(button);
                        b3.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b3.setTextColor(Color.parseColor("#ffffff"));
                        button = 3;
                        tiaoliyuanze.setText("调理原则：理气行气 疏肝解郁");
                        tiaolineirong.setText("气郁的女性，因为以前有过重大疾病损伤元气，或者情感问题困扰心情不畅，会有乳房乳头胀痛，除外还会伴随烦躁不安、胸闷、肋骨抽痛、脸上长黑斑等。解决这个问题，首先要调整自己的心态，配合理气和胃的陈皮，佛手等，另外适当运动，去景色好的地方放松一下自己紧张的情绪。");
                    }
                    break;
                case R.id.pinghezhi_button4:                            //登录界面的注册按钮
                    if (button != 4) {
                        changebutton(button);
                        b4.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b4.setTextColor(Color.parseColor("#ffffff"));
                        button = 4;
                        tiaoliyuanze.setText("调理原则：疏肝解郁，理气化痰");
                        tiaolineirong.setText("咽部总有一种感觉，里面有东西，咳不出来也咽不下去。这个问题主要因情志不畅，肝气郁结，气机紊乱，本该下降的气机却向上乱窜，结于咽喉形成的。解决这个问题首先还是要梳理自己的情绪，适当运动，然后用一些理气化痰的食物，如陈皮，佛手等。");
                    }
                    break;
                case R.id.pinghezhi_button5:                            //登录界面的注册按钮
                    if (button != 5) {
                        changebutton(button);
                        b5.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b5.setTextColor(Color.parseColor("#ffffff"));
                        button = 5;
                        tiaoliyuanze.setText("调理原则：健脾，疏肝");
                        tiaolineirong.setText("生活压力大，情绪不好的人会有胃部反酸的现象。这个问题主要也是因为肝气太旺，横逆犯胃，胃气上逆导致胃部不适，反酸水，没有食欲，不想吃饭，而且饭后感觉消化不掉这些食物。解决这个问题，不仅要健脾，还有疏肝。");
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
            case 5:
                b5.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze);
                b5.setTextColor(Color.parseColor("#2ebf91"));
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
