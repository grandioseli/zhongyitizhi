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

public class yinxuzhi extends AppCompatActivity {
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
        setContentView(R.layout.yinxuzhi);
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
                Intent intent = new Intent(yinxuzhi.this, tizhi.class) ;    //切换Login Activity至User Activity
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
                        tiaoliyuanze.setText("调理原则：滋阴润肺");
                        tiaolineirong.setText("一阴一阳谓之道，健康的人是阴阳平衡，温暖和润，不寒不热，而阴虚是体内阴血、津液不足，不能濡养皮肤官窍，常出现皮肤干、口唇干、口干口渴、眼睛干、鼻子干、鼻出血等。肺主皮毛，开窍于鼻，且口鼻相通，所以要想滋润皮肤口鼻，还需滋阴润肺。");
                    }
                    break;
                case R.id.pinghezhi_button2:                              //登录界面的登录按钮
                    if (button != 2) {
                        changebutton(button);
                        b2.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b2.setTextColor(Color.parseColor("#ffffff"));
                        button = 2;
                        tiaoliyuanze.setText("调理原则：滋阴补血、生津润燥");
                        tiaolineirong.setText("简单的理解，阴虚质就是体内缺水少水，而水性寒凉而润下，水不足则无以滋润肠道，出现便秘、大便干结，甚至像羊粪一样，一粒一粒的。这样的便秘用苦寒去火之法往往效果不好，还会伤脾胃阳气，应该滋阴补血、生津润燥。");
                    }
                    break;
                case R.id.pinghezhi_button3:                            //登录界面的注册按钮
                    if (button != 3) {
                        changebutton(button);
                        b3.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b3.setTextColor(Color.parseColor("#ffffff"));
                        button = 3;
                        tiaoliyuanze.setText("调清热利尿、泻火除烦");
                        tiaolineirong.setText("在人体，阴阳水火是相互依存、制约的。阴寒阳热，当阴水不足，自然不能制衡阳热，就容易内热上火，比如身热烦躁、眼睛红肿、咽喉肿痛、脸上长痘、牙痛、牙龈出血、口疮、口苦口臭、便秘尿黄等。这时候最需要的是清热泻火，通便利尿。");
                    }
                    break;
                case R.id.pinghezhi_button4:                            //登录界面的注册按钮
                    if (button != 4) {
                        changebutton(button);
                        b4.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b4.setTextColor(Color.parseColor("#ffffff"));
                        button = 4;
                        tiaoliyuanze.setText("调理原则：滋阴补血，养心安神");
                        tiaolineirong.setText("阴虚的人往往血虚，阴血不足，心神就得不到滋养，出现心神不安、烦躁失眠；同时阴虚不能制约阳气，亢盛的阳气也来扰动心神，让你睡不着、睡眠浅、烦躁，有些人即便睡着了也会出很多汗。调理应滋阴补血，养心安神。");
                    }
                    break;
                case R.id.pinghezhi_button5:                            //登录界面的注册按钮
                    if (button != 5) {
                        changebutton(button);
                        b5.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b5.setTextColor(Color.parseColor("#ffffff"));
                        button = 5;
                        tiaoliyuanze.setText("滋阴养血、补肝肾");
                        tiaolineirong.setText("肝开窍于目，阴虚得人，肝血不足，肝肾阴虚，眼睛得不到肝血濡养就会出现干涩的症状。“久试伤血”，不要长时间对着电脑，除此之外，还可以滋阴养血、补肝肾。");
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
