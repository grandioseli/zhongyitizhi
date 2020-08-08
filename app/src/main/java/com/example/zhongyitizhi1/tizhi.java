package com.example.zhongyitizhi1;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class tizhi extends AppCompatActivity {
    //下面是九个体质button，当用户选定时，由int值tizhi来确定对象
    private Button tizhi_button1;
    private Button tizhi_button2;
    private Button tizhi_button3;
    private Button tizhi_button4;
    private Button tizhi_button5;
    private Button tizhi_button6;
    private Button tizhi_button7;
    private Button tizhi_button8;
    private Button tizhi_button9;
    private MyDatabaseHelper dbHelper;              //sqlite类
    private int tizhi = 0;
    private int physique = 0;//用于存储从sqlit中读取到的体质
    private String physique1 = "";
    private String phone = "";//用于存储用户电话
    private int gender = 0; //用户存储用户性别
    private String gender1 = "";
    private String username;//用于存储读取到的当前用户名
    //确定体质button
    private Button tizhiqueding;
    private Button test;
    //照片uri
    Uri uri;
    //这里是需要动态申请的权限
    private static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    //回调标志
    private static final int CAMERA_PERMISSIONS_CODE = 1;
    private static final int CAMERA_TAKE_PHOTO = 2;
    private static final int CAMERA_CHOOSE_PICTURE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //常规
        super.onCreate(savedInstanceState);
        //下两行代码防止相机崩溃
        //N表示24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        setContentView(R.layout.tizhi);
        test = findViewById(R.id.kaishiceshi);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        tizhi_button1 = findViewById(R.id.tizhi_button1);
        tizhi_button2 = findViewById(R.id.tizhi_button2);
        tizhi_button3 = findViewById(R.id.tizhi_button3);
        tizhi_button4 = findViewById(R.id.tizhi_button4);
        tizhi_button5 = findViewById(R.id.tizhi_button5);
        tizhi_button6 = findViewById(R.id.tizhi_button6);
        tizhi_button7 = findViewById(R.id.tizhi_button7);
        tizhi_button8 = findViewById(R.id.tizhi_button8);
        tizhi_button9 = findViewById(R.id.tizhi_button9);
        tizhiqueding = findViewById(R.id.tizhiqueding);
        tizhi_button1.setOnClickListener(mListener);
        tizhi_button2.setOnClickListener(mListener);
        tizhi_button3.setOnClickListener(mListener);
        tizhi_button4.setOnClickListener(mListener);
        tizhi_button5.setOnClickListener(mListener);
        tizhi_button6.setOnClickListener(mListener);
        tizhi_button7.setOnClickListener(mListener);
        tizhi_button8.setOnClickListener(mListener);
        tizhi_button9.setOnClickListener(mListener);
        tizhiqueding.setOnClickListener(mListener);
        test.setOnClickListener(mListener);
        //sqlite类
        dbHelper = new MyDatabaseHelper(this, "mydb.db3",1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("userin", null, null, null, null, null, null);
        //如果游标不为0，说明已经曾经有用户登录成功过
        if (cursor.moveToFirst()) {
            System.out.println("读取到用户信息");
            username = cursor.getString(1);
            System.out.println("记录用户名是" + username);
        }
        //从数据库中找到用户的数据
        Cursor cursor1 = db.rawQuery(" select * from user where username = ? ",new String[]{username});
        if (cursor1.moveToFirst()) {
            phone = cursor1.getString(2);
            gender = cursor1.getInt(3);
            if(gender == 0)
            {
                gender1= "无记录";
            }
            else if(gender ==1)
            {
                gender1 ="man";
            }
            else if(gender ==2)
            {
                gender1 = "woman";
            }
            else
                {
                 gender1= "无记录";
                }
            physique = cursor1.getInt(4); //获取体质
            System.out.println("当前用户的性别为" + physique);
        }
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tizhi_button1:                            //登录界面的注册按钮
                    if(tizhi !=1) {
                        changecolor(tizhi);
                        tizhi_button1.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button1.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 1;
                    }
                    else
                    {
                        tizhi_button1.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button1.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button2:                              //登录界面的登录按钮
                    if(tizhi !=2) {
                        changecolor(tizhi);
                        tizhi_button2.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button2.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 2;
                    }
                    else
                    {
                        tizhi_button2.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button2.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button3:                            //登录界面的注册按钮
                    if(tizhi !=3) {
                        changecolor(tizhi);
                        tizhi_button3.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button3.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 3;
                    }
                    else
                    {
                        tizhi_button3.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button3.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button4:                            //登录界面的注册按钮
                    if(tizhi !=4) {
                        changecolor(tizhi);
                        tizhi_button4.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button4.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 4;
                    }
                    else
                    {
                        tizhi_button4.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button4.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button5:                            //登录界面的注册按钮
                    if(tizhi !=5) {
                        changecolor(tizhi);
                        tizhi_button5.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button5.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 5;
                    }
                    else
                    {
                        tizhi_button5.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button5.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button6:                            //登录界面的注册按钮
                    if(tizhi !=6) {
                        changecolor(tizhi);
                        tizhi_button6.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button6.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 6;
                    }
                    else
                    {
                        tizhi_button6.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button6.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button7:                            //登录界面的注册按钮
                    if(tizhi !=7) {
                        changecolor(tizhi);
                        tizhi_button7.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button7.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 7;
                    }
                    else
                    {
                        tizhi_button7.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button7.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button8:                            //登录界面的注册按钮
                    if(tizhi !=8) {
                        changecolor(tizhi);
                        tizhi_button8.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button8.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 8;
                    }
                    else
                    {
                        tizhi_button8.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button8.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.tizhi_button9:                            //登录界面的注册按钮
                    if(tizhi !=9) {
                        changecolor(tizhi);
                        tizhi_button9.setBackgroundColor(Color.parseColor("#45B649"));
                        tizhi_button9.setTextColor(Color.parseColor("#ffffff"));
                        tizhi = 9;
                    }
                    else
                    {
                        tizhi_button9.setBackgroundResource(R.drawable.tizhi_button);
                        tizhi_button9.setTextColor(Color.parseColor("#2ebf91"));
                        tizhi = 0;
                    }
                    break;
                case R.id.kaishiceshi:
                    requestPermission();
                    //添加仿ios底部dialog，添加拍照上传按钮
                    new ActionSheetDialog(tizhi.this).builder().setTitle("上传车辆照片")
                            .setCancelable(false).setCanceledOnTouchOutside(false)
                            .addSheetItem("拍照上传", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    //获取uri
                                    uri = getOutputMediaFileUri(tizhi.this);
                                    //检视用
                                    System.out.println(uri);
                                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                    //Android7.0添加临时权限标记，此步千万别忘了
                                    openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                    //打开相机
                                    startActivityForResult(openCameraIntent, CAMERA_TAKE_PHOTO);
                                }
                            }).addSheetItem("相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            // 相册选取
                            Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(intent1, CAMERA_CHOOSE_PICTURE);
                        }
                    }).show();
                    break;
                case R.id.tizhiqueding:
                    physique1 = String.valueOf(tizhi);
                    //如果为0，说明尚未选择过体质，直接绑定然后返回提示信息
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = HttpUtilsHttpURLConnection.BASE_URL + "/Complete";
                            Map<String, String> params = new HashMap<String, String>();
                            //向map赋值
                            params.put("name", username);
                            params.put("phone", phone);
                            params.put("gender", gender1);
                            params.put("physique", physique1);
                            String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                            System.out.println("结果是" + result);
                        }
                    }).start();
                    if (physique == 0) {
                        Toasty.success(tizhi.this, "设定体质成功!", Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.info(tizhi.this, "您修改了您的体质!", Toast.LENGTH_SHORT, true).show();
                    }
                    break;
            }
        }
    };
    public void changecolor(int i)
    {
        switch(i)
        {
            case 1:
                tizhi_button1.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button1.setTextColor(Color.parseColor("#2ebf91"));
            case 2:
                tizhi_button2.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button2.setTextColor(Color.parseColor("#2ebf91"));
            case 3:
                tizhi_button3.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button3.setTextColor(Color.parseColor("#2ebf91"));
            case 4:
                tizhi_button4.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button4.setTextColor(Color.parseColor("#2ebf91"));
            case 5:
                tizhi_button5.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button5.setTextColor(Color.parseColor("#2ebf91"));
            case 6:
                tizhi_button6.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button6.setTextColor(Color.parseColor("#2ebf91"));
            case 7:
                tizhi_button7.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button7.setTextColor(Color.parseColor("#2ebf91"));
            case 8:
                tizhi_button8.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button8.setTextColor(Color.parseColor("#2ebf91"));
            case 9:
                tizhi_button9.setBackgroundResource(R.drawable.tizhi_button);
                tizhi_button9.setTextColor(Color.parseColor("#2ebf91"));
        }
    }
    //获取uri
    public  Uri getOutputMediaFileUri(Context context) {
        //测试新的路径
        //创建路径
        String filePath = getExternalFilesDir(null).getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
        //创建file文件
        File outputFile = new File(filePath);
        //如果上述定义的路径不存在则创建路径
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        //根据sdk版本创建uri
        Uri contentUri = null;
        if(Build.VERSION.SDK_INT < 24){
            contentUri = Uri.fromFile(outputFile);
        }
        else{
            contentUri = FileProvider.getUriForFile(this,
                    context.getApplicationContext().getPackageName() + ".provider", outputFile);
            System.out.println(contentUri);
        }
        return contentUri;
    }
    //动态申请权限
    private void requestPermission() {
        // 当API大于 23 （M表示23）时，才动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(tizhi.this,CAMERA_PERMISSIONS,CAMERA_PERMISSIONS_CODE);
        }
    }
    //处理权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_CODE:
                //权限请求失败
                if (grantResults.length == CAMERA_PERMISSIONS.length) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            //弹出对话框引导用户去设置
                            showDialog();
                            Toast.makeText(tizhi.this, "请求权限被拒绝", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }else{
                    Toast.makeText(tizhi.this, "已授权", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    //弹出提示框
    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("体质监测需要相机、录音和读写权限，是否去设置？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToAppSetting();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
    // 跳转到当前应用的设置界面
    private void goToAppSetting(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_TAKE_PHOTO:
                //如果拍照成功并且存放到指定uri
                if (resultCode == Activity.RESULT_OK) {
                    //在img中显示图片
//                    iv_photo.setImageURI(uri);
                    System.out.println("相机拍照成功了");
                }
                System.out.println(resultCode);
                break;
            case CAMERA_CHOOSE_PICTURE:
                if(data!=null) {
                    Bitmap bm = null;
                    // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                    ContentResolver resolver = getContentResolver();

                    try {
                        Uri originalUri = data.getData(); // 获得图片的uri
                        System.out.println("图片的uri为"+originalUri);
//                        iv_photo.setImageURI(originalUri);

                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
                        // 这里开始的第二部分，获取图片的路径：

                        String[] proj = {MediaStore.Images.Media.DATA};

                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
                        @SuppressWarnings("deprecation")
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        // 最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);
                        System.out.println("图片的path:"+path);
//                    iv_photo.setImageURI(originalUri);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("您取消选择相册");
                }
                break;
            case 104:// 取得裁剪后的图片
                if (data != null) {
                    //setPicToView(data);
//                    iv_photo.setImageURI(uri);
                    System.out.println("待补充裁剪处理");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
