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

public class tanshizhi extends AppCompatActivity {
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
        setContentView(R.layout.tanshizhi);
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
                Intent intent = new Intent(tanshizhi.this, tizhi.class) ;    //切换Login Activity至User Activity
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
                        tiaoliyuanze.setText("调理原则:控油、祛痘");
                        tiaolineirong.setText("皮肤、汗液、大小便是身体排毒的重要出口，痰湿的人因为过多的痰湿毒素不能通过汗、大小便及时排出，所以就从皮肤找出口，使皮肤油腻、长痘痘、脓疮等。解决这个问题，首先要消除痰湿之源。脾不健运，湿聚成痰者，当健脾化痰。二要疏通毛孔，帮助排毒。");
                    }
                    break;
                case R.id.pinghezhi_button2:                              //登录界面的登录按钮
                    if (button != 2) {
                        changebutton(button);
                        b2.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b2.setTextColor(Color.parseColor("#ffffff"));
                        button = 2;
                        tiaoliyuanze.setText("调理原则：疏肝健脾，消积化痰、健脾祛湿");
                        tiaolineirong.setText("脾主运化，它协调管理营养和废物的运送，而痰湿的人往往因为脾虚不能把吃进去的东西转化为气血，却形成半成品————痰湿，堆积在腹部、臀部等部位而发胖，而且身体感觉沉重不爱动。减重应健脾祛湿、化痰消食。");
                    }
                    break;
                case R.id.pinghezhi_button3:                            //登录界面的注册按钮
                    if (button != 3) {
                        changebutton(button);
                        b3.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b3.setTextColor(Color.parseColor("#ffffff"));
                        button = 3;
                        tiaoliyuanze.setText("调理原则：健脾祛湿、化痰补气");
                        tiaolineirong.setText("便便粘在马桶上，冲好几次水也冲不掉，用好几张纸才能擦干净屁股；或者大便不成形，这是体内湿气或湿热太重的原因。调理应健脾、祛湿化痰，健脾则切断生痰湿之源，祛湿化痰是给痰湿一个出路");
                    }
                    break;
                case R.id.pinghezhi_button4:                            //登录界面的注册按钮
                    if (button != 4) {
                        changebutton(button);
                        b4.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b4.setTextColor(Color.parseColor("#ffffff"));
                        button = 4;
                        tiaoliyuanze.setText("调理原则：健脾化痰、祛湿补气");
                        tiaolineirong.setText("湿气黏浊向下，痰湿时感觉身体沉重，尤其是双腿；头重头蒙，昏昏沉沉，整个人无精打采。要想精力充沛，需要祛湿化痰，健脾补气。");
                    }
                    break;
                case R.id.pinghezhi_button5:                            //登录界面的注册按钮
                    if (button != 5) {
                        changebutton(button);
                        b5.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b5.setTextColor(Color.parseColor("#ffffff"));
                        button = 5;
                        tiaoliyuanze.setText("调理原则：健脾祛湿，化痰安神");
                        tiaolineirong.setText("湿气会阻滞人体正常的气机循环，脏腑之气不畅通，进而经络气血不通，容易引起脑补气血不畅，睡眠质量差。调理除了祛湿外，还应该疏通经络。");
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
