package com.example.zhongyitizhi1;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

public class Myadvice extends AppCompatActivity {
    private Button q1;
    private Button q2;
    private Button q3;
    private Button q4;
    private ImageView photo;
    private int question = 0;//问题类型，1，2，3，4分别对应四个问题类型
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
        setContentView(R.layout.myadvice);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        //绑定问题类型
        q1= findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        //绑定拍照上传
        photo = findViewById(R.id.photo);
        //绑定问题函数
        q1.setOnClickListener(mListener);
        q2.setOnClickListener(mListener);
        q3.setOnClickListener(mListener);
        q4.setOnClickListener(mListener);
        photo.setOnClickListener(mListener);
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.q1:                            //登录界面的注册按钮
                    if(question !=1) {
                        changecolor(question);
                        q1.setBackgroundColor(Color.parseColor("#45B649"));
                        q1.setTextColor(Color.parseColor("#ffffff"));
                        question = 1;
                    }
                    else
                    {
                        q1.setBackgroundResource(R.drawable.tizhi_button);
                        q1.setTextColor(Color.parseColor("#2ebf91"));
                        question = 0;
                    }
                    break;
                case R.id.q2:                              //登录界面的登录按钮
                    if(question !=2) {
                        changecolor(question);
                        q2.setBackgroundColor(Color.parseColor("#45B649"));
                        q2.setTextColor(Color.parseColor("#ffffff"));
                        question = 2;
                    }
                    else
                    {
                        q2.setBackgroundResource(R.drawable.tizhi_button);
                        q2.setTextColor(Color.parseColor("#2ebf91"));
                        question = 0;
                    }
                    break;
                case R.id.q3:                            //登录界面的注册按钮
                    if(question !=3) {
                        changecolor(question);
                        q3.setBackgroundColor(Color.parseColor("#45B649"));
                        q3.setTextColor(Color.parseColor("#ffffff"));
                        question = 3;
                    }
                    else
                    {
                        q3.setBackgroundResource(R.drawable.tizhi_button);
                        q3.setTextColor(Color.parseColor("#2ebf91"));
                        question = 0;
                    }
                    break;
                case R.id.q4:                            //登录界面的注册按钮
                    if(question !=4) {
                        changecolor(question);
                        q4.setBackgroundColor(Color.parseColor("#45B649"));
                        q4.setTextColor(Color.parseColor("#ffffff"));
                        question = 4;
                    }
                    else
                    {
                        q4.setBackgroundResource(R.drawable.tizhi_button);
                        q4.setTextColor(Color.parseColor("#2ebf91"));
                        question = 0;
                    }
                    break;
                case R.id.photo:
                    requestPermission();
                    //添加仿ios底部dialog，添加拍照上传按钮
                    new ActionSheetDialog(Myadvice.this).builder().setTitle("上传图片")
                            .setCancelable(false).setCanceledOnTouchOutside(false)
                            .addSheetItem("拍照上传", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    //获取uri
                                    uri = getOutputMediaFileUri(Myadvice.this);
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
            }
        }
    };
    //重写这个方法就可以实现toolbar的返回，目前尚未看懂
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void changecolor(int i)
    {
        switch(i)
        {
            case 1:
                q1.setBackgroundResource(R.drawable.tizhi_button);
                q1.setTextColor(Color.parseColor("#2ebf91"));
            case 2:
                q2.setBackgroundResource(R.drawable.tizhi_button);
                q2.setTextColor(Color.parseColor("#2ebf91"));
            case 3:
                q3.setBackgroundResource(R.drawable.tizhi_button);
                q3.setTextColor(Color.parseColor("#2ebf91"));
            case 4:
                q4.setBackgroundResource(R.drawable.tizhi_button);
                q4.setTextColor(Color.parseColor("#2ebf91"));

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
            ActivityCompat.requestPermissions(Myadvice.this,CAMERA_PERMISSIONS,CAMERA_PERMISSIONS_CODE);
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
                            Toast.makeText(Myadvice.this, "请求权限被拒绝", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }else{
                    Toast.makeText(Myadvice.this, "已授权", Toast.LENGTH_LONG).show();
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
                    photo.setImageURI(uri);
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
                        photo.setImageURI(originalUri);
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
}
