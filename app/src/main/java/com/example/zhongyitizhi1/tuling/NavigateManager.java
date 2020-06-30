package com.example.zhongyitizhi1.tuling;

import android.content.Context;
import android.content.Intent;


public class NavigateManager {

    public static void gotoAboutActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public static void gotoNewsActivity(Context context, MessageEntity messageEntity) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra("messageEntity", messageEntity);
        context.startActivity(intent);
    }

    public static void gotoDetailActivity(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
