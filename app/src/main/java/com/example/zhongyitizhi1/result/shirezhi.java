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

public class shirezhi extends AppCompatActivity {
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
        setContentView(R.layout.shirezhi);
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
                Intent intent = new Intent(shirezhi.this, tizhi.class) ;    //切换Login Activity至User Activity
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
                        tiaoliyuanze.setText("调理原则：健脾祛湿、控油祛痘");
                        tiaolineirong.setText("皮肤、汗、大小便是身体排毒的重要出口，湿热重的人因为过多的湿毒热毒不能通过汗、大小便及时排出，所以就从皮肤找出口，使皮肤油腻、长痘痘、脓疮等。这时一要健壮脾胃，消除生痰湿之源，二要疏通毛孔，帮助排毒。");
                    }
                    break;
                case R.id.pinghezhi_button2:                              //登录界面的登录按钮
                    if (button != 2) {
                        changebutton(button);
                        b2.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b2.setTextColor(Color.parseColor("#ffffff"));
                        button = 2;
                        tiaoliyuanze.setText("清热祛湿、健脾降气");
                        tiaolineirong.setText("大家都知道，在湿热的环境中，东西非常容易腐败，产生难闻的味道，湿热质的口臭就是因为脾胃湿热，食物不能很好的代谢，产生异味，向上熏蒸引起的。调养要特别注意养脾胃，少肉多素，细嚼慢咽、七分饱，同时吃一些健脾降气、清热祛湿的食物。");
                    }
                    break;
                case R.id.pinghezhi_button3:                            //登录界面的注册按钮
                    if (button != 3) {
                        changebutton(button);
                        b3.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b3.setTextColor(Color.parseColor("#ffffff"));
                        button = 3;
                        tiaoliyuanze.setText("调理原则：健脾祛湿、补气血");
                        tiaolineirong.setText("便便粘在马桶上，冲好几次水也冲不掉，用好几张纸才能擦干净屁股；或者大便不成形，这是体内湿气或湿热太重的原因。调理应健脾祛湿，健脾则运化水湿的能力增强，湿气排出大便自然就不那么黏滞了。");
                    }
                    break;
                case R.id.pinghezhi_button4:                            //登录界面的注册按钮
                    if (button != 4) {
                        changebutton(button);
                        b4.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b4.setTextColor(Color.parseColor("#ffffff"));
                        button = 4;
                        tiaoliyuanze.setText("调理原则：清热祛湿");
                        tiaolineirong.setText("湿气重的人，因体内湿与热勾结，使湿热郁积在皮肤表面，散发出一股汗臭味或狐臭味，尤其是出汗后。要想去除或减轻身体的味道，除了注意卫生勤洗澡勤换衣服外，还应清热祛湿，从根本上解决。");
                    }
                    break;
                case R.id.pinghezhi_button5:                            //登录界面的注册按钮
                    if (button != 5) {
                        changebutton(button);
                        b5.setBackgroundResource(R.drawable.yuanjiaojuzhen_tiaoliyuanze2);
                        b5.setTextColor(Color.parseColor("#ffffff"));
                        button = 5;
                        tiaoliyuanze.setText("健脾祛湿、疏肝行气");
                        tiaolineirong.setText("湿性黏浊、向下走，而且湿与热喜欢勾结在一起，当湿热一起往下走时，男人容易出现阴囊潮湿，女人容易出现白带多而黄，妇科炎症复发或加重，迁延不愈。要想让自己清爽不浊，需要健脾祛湿、疏肝行气。");
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
