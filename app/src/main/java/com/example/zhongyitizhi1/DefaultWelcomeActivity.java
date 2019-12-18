package com.example.zhongyitizhi1;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * Created by stephentuso on 10/5/16.
 */
//
public class DefaultWelcomeActivity extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorPrimary)
                .page(new TitlePage(R.drawable.ic_loyalty_black_24dp, "欢迎").background(R.color.anlanyan))
                .page(new BasicPage(R.drawable.ic_contacts_white_24dp, "体质", "我的体质，一测便知").background(R.color.chunlvse))
                .page(new BasicPage(R.drawable.ic_local_hospital_white_24dp, "健康", "健康咨询，在线答疑").background(R.color.colorAccent))
                .page(new BasicPage(R.drawable.ic_bluetooth_audio_white_24dp, "链接", "蓝牙连接，方便快捷").background(R.color.tianlanse))
                .build();
    }

}
