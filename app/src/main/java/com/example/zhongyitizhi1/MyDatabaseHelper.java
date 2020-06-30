package com.example.zhongyitizhi1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(Context context, String name, int version)
    {
        super(context, name, null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //第一次使用数据库时自动建表
        //这个表用于标志自动登录
        db.execSQL("create table autologin("
                + "number integer primary key,"
                + "isautologin integer )");
        //这个表用于存储用户信息
        db.execSQL("create table user("
                + "username string primary key,"
                + "password string,"
                + "user_phone string,"
                + "gender integer,"
                + "physique integer)");
        //这个表用于存储最近登录的用户，所有的显示信息以他为准
        db.execSQL("create table userin("
                + "number integer primary key,"
                + "username string )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion , int newVersion)
    {
        System.out.println("-------onUpgrade Called----------"
        + oldVersion + "----->" + newVersion);//当数据库发生更新时，在此处更新数据库的表
    }
}
