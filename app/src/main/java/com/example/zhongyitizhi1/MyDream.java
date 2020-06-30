package com.example.zhongyitizhi1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MyDream extends AppCompatActivity {
    private Integer imgstate1;
    private Integer imgstate2;
    private Integer imgstate3;
    private Integer imgstate4;
    private Integer imgstate5;
    private Integer imgstate6;
    private Integer imgstate7;
    private Integer imgstate8;
    private Integer imgstate9;
    private ImageView gou1;
    private ImageView gou2;
    private ImageView gou3;
    private ImageView gou4;
    private ImageView gou5;
    private ImageView gou6;
    private ImageView gou7;
    private ImageView gou8;
    private ImageView gou9;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView t6;
    private TextView t7;
    private TextView t8;
    private TextView t9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //常规
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydream);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        //绑定各个图片
        gou1 = findViewById(R.id.gou1);
        gou2 = findViewById(R.id.gou2);
        gou3 = findViewById(R.id.gou3);
        gou4 = findViewById(R.id.gou4);
        gou5 = findViewById(R.id.gou5);
        gou6 = findViewById(R.id.gou6);
        gou7 = findViewById(R.id.gou7);
        gou8 = findViewById(R.id.gou8);
        gou9 = findViewById(R.id.gou9);
        //绑定各个textview
        t1 =findViewById(R.id.t1);
        t2 =findViewById(R.id.t2);
        t3 =findViewById(R.id.t3);
        t4 =findViewById(R.id.t4);
        t5 =findViewById(R.id.t5);
        t6 =findViewById(R.id.t6);
        t7 =findViewById(R.id.t7);
        t8 =findViewById(R.id.t8);
        t9 = findViewById(R.id.t9);
        View view1 = findViewById(R.id.bushen);
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou1.getVisibility() == View.INVISIBLE )
                {gou1.setVisibility(View.VISIBLE);
                t1.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou1.setVisibility(View.INVISIBLE);
                    t1.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view2 = findViewById(R.id.kangshuailao);
        view2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou2.getVisibility() == View.INVISIBLE)
                {gou2.setVisibility(View.VISIBLE);
                t2.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou2.setVisibility(View.INVISIBLE);
                    t2.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view3 = findViewById(R.id.qushixiaozhong);
        view3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou3.getVisibility() == View.INVISIBLE)
                {gou3.setVisibility(View.VISIBLE);
                    t3.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou3.setVisibility(View.INVISIBLE);
                    t3.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view4 = findViewById(R.id.shuhuanyali);
        view4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou4.getVisibility() == View.INVISIBLE)
                {gou4.setVisibility(View.VISIBLE);
                    t4.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou4.setVisibility(View.INVISIBLE);
                    t4.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view5 = findViewById(R.id.zengqiangmianyili);
        view5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou5.getVisibility() == View.INVISIBLE)
                {gou5.setVisibility(View.VISIBLE);
                    t5.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou5.setVisibility(View.INVISIBLE);
                    t5.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view6 = findViewById(R.id.jinglichongpei);
        view6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou6.getVisibility() == View.INVISIBLE)
                {gou6.setVisibility(View.VISIBLE);
                    t6.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou6.setVisibility(View.INVISIBLE);
                    t6.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view7 = findViewById(R.id.haoqise);
        view7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou7.getVisibility() == View.INVISIBLE)
                {gou7.setVisibility(View.VISIBLE);
                    t7.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou7.setVisibility(View.INVISIBLE);
                    t7.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view8 = findViewById(R.id.yangfeiyiqi);
        view8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou8.getVisibility() == View.INVISIBLE)
                {gou8.setVisibility(View.VISIBLE);
                    t8.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou8.setVisibility(View.INVISIBLE);
                    t8.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
        View view9 = findViewById(R.id.haoshuimian);
        view9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gou9.getVisibility() == View.INVISIBLE)
                {gou9.setVisibility(View.VISIBLE);
                    t9.setTextColor(Color.parseColor("#2ebf91"));}
                else
                {
                    gou9.setVisibility(View.INVISIBLE);
                    t9.setTextColor(Color.parseColor("#8A000000"));
                }
            }
        });
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
