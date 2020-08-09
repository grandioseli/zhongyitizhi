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

public class yangxuzhi extends AppCompatActivity {
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
        setContentView(R.layout.yangxuzhi);
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
                Intent intent = new Intent(yangxuzhi.this, tizhi.class) ;    //切换Login Activity至User Activity
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
                        tiaoliyuanze.setText("调理原则:补肾阳、填肾精");
                        tiaolineirong.setText("阳虚的体质意味着肾的阳气不足，肾其华在发，也就是说肾精充足，头发质量才好。肾精不足，头发掉得又多又快，长得又慢又少。补足肾阳是改善掉发的关键。");
                    }
                    break;
                case R.id.pinghezhi_button2:                              //登录界面的登录按钮
                    if (button != 2) {
                        changebutton(button);
                        b2.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b2.setTextColor(Color.parseColor("#ffffff"));
                        button = 2;
                        tiaoliyuanze.setText("调理原则：补脾阳，提高小肠温度");
                        tiaolineirong.setText("体质阳虚，又长期不注意保护小腹温度的，容易习惯性腹泻，喝一点冷的东西，吹一点冷风都会腹泻。《素问·灵兰秘典论》说：“小肠者，受盛之官，化物出焉。” 小肠受盛、化物和泌别清浊的功能，在水谷化为精微的过程中是十分重要的，而这些功能都要靠小肠的温煦的力量完成的，如果温度不够，就会有便溏、泄泻等症。");
                    }
                    break;
                case R.id.pinghezhi_button3:                            //登录界面的注册按钮
                    if (button != 3) {
                        changebutton(button);
                        b3.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b3.setTextColor(Color.parseColor("#ffffff"));
                        button = 3;
                        tiaoliyuanze.setText("调理原则：温补脾肾");
                        tiaolineirong.setText("血脉得温则行，遇寒则凝，长期过食生冷寒凉、不注意保暖等寒气滞留血脉，导致血脉瘀阻不通。需要把寒气化开，才能使气血通畅，改善痛经。");
                    }
                    break;
                case R.id.pinghezhi_button4:                            //登录界面的注册按钮
                    if (button != 4) {
                        changebutton(button);
                        b4.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b4.setTextColor(Color.parseColor("#ffffff"));
                        button = 4;
                        tiaoliyuanze.setText("调理原则：温补肾阳");
                        tiaolineirong.setText("体内的寒气很重时，会导致肾阳不足，就是说这时你身体的火力不足了，膀胱本来应该靠着肾阳的力量存满尿液，但是由于肾阳不足，膀胱气化无力，而造成起夜，尿频。可以睡前用艾灸贴或暖宝宝热敷小肚子，平时注意起居饮食，少吃生冷食物。");
                    }
                    break;
                case R.id.pinghezhi_button5:                            //登录界面的注册按钮
                    if (button != 5) {
                        changebutton(button);
                        b5.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b5.setTextColor(Color.parseColor("#ffffff"));
                        button = 5;
                        tiaoliyuanze.setText("调理原则：温阳补气");
                        tiaolineirong.setText("有很多体寒的人，经常感觉手脚冰凉，经常晚上睡了一夜早上脚还是凉的。需要首先用温通的方法将身体的经络打开，祛除积年的寒邪，让阳气得以正常生发。除了平时要注意保暖，少食生冷食物以外。可以采用艾灸温经除寒。");
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
