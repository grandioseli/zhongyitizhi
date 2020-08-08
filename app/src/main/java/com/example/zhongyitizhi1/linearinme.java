package com.example.zhongyitizhi1;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.zhongyitizhi1.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class linearinme extends AppCompatActivity {
    //设定几个textview
    private TextView usernameInText;
    private TextView phoneInText;
    private TextView genderInText;
    private String username;//用于存储读取到的当前用户名
    private MyDatabaseHelper dbHelper;              //sqlite类
    private String phone = "";//电话，如果为0则表示为空
    private int gender = 0;//再次提醒1为男，2为女
    private String gender1;//字符型性别表示
    private String gender2="man";//这个gender用于在dialog中改变性别
    private int physique = 0;//用于存储用户体质
    private String physique1= "";
    private ImageView touxiang;//个人头像
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
        setContentView(R.layout.linearinme);
        Toolbar toolbar = findViewById(R.id.toolbar5);
        usernameInText= (TextView) findViewById(R.id.xingming2);
        phoneInText = (TextView) findViewById(R.id.shoujihao2);
        genderInText = (TextView) findViewById(R.id.xingbie2);
        touxiang = (ImageView) findViewById(R.id.gerentouxiang);
        //下两行代码防止相机崩溃
        //N表示24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        //创建datahelper类
        dbHelper = new MyDatabaseHelper(this, "mydb.db3",1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //获取到最新的用户名
        Cursor cursor = db.query("userin", null, null, null, null, null, null);
        //如果游标不为0，说明已经曾经有用户登录成功过
        if (cursor.moveToFirst()) {
            System.out.println("读取到用户信息");
            username = cursor.getString(1);
            System.out.println("记录用户名是" + username);
        }
        //根据上述读取到的用户名到sqlite的user表查找用户信息
        Cursor cursor1 = db.rawQuery(" select * from user where username = ? ",new String[]{username});
        if (cursor1.moveToFirst()) {
            phone = cursor1.getString(2); //获取电话
            gender = cursor1.getInt(3);//获取性别
            physique = cursor1.getInt(4);//获取体质，保证后面上传的时候不改变用户体质
            physique1 = String.valueOf(physique);
            System.out.println("当前用户的电话为" + phone);
            System.out.println("当前用户的性别为" + gender);
        }
        if(gender == 1)
        {
            gender1 = "男";
        }
        else if(gender == 2)
        {
            gender1 = "女";
        }
        else
            {
               gender1="无记录" ;
            }
        usernameInText.setText(username);
        genderInText.setText(gender1);
        if(phone!="0") {
            phoneInText.setText(phone);
        }
        else
        {
            phoneInText.setText("无记录");
        }
        //为头像窗口设置选择头像
        LinearLayoutCompat l1 = findViewById(R.id.xiugaitouxiang);
        l1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //申请相关权限
                requestPermission();
                new ActionSheetDialog(linearinme.this).builder().setTitle("更换头像")
                        .setCancelable(false).setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照上传", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //获取uri
                                uri = getOutputMediaFileUri(linearinme.this);
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
            }
        });
        //为性别设置dialog使得用户可以更改
        LinearLayoutCompat l2 = findViewById(R.id.xingbie);
        l2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//                SQLiteDatabase db3 = dbHelper.getWritableDatabase();
//                Cursor cursor3 = db3.rawQuery(" select * from user where username = ? ",new String[]{username});
//                if (cursor3.moveToFirst()) {
//                    gender = cursor3.getInt(3);//获取性别
//                }
                AlertDialog.Builder builder = new AlertDialog.Builder(linearinme.this);
                builder.setTitle("您的性别是");
                final String[] items = { "男","女" };
                // -1代表没有条目被选中
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // [1]把选择的条目给取出来
                        String item = items[which];
                        ContentValues values = new ContentValues();
                        values.put("gender", which+1);
                        SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                        db2.update("user", values, "username = ?",new String[]{username} );
                        genderInText.setText(item);
                        if(which==0)
                        {
                            gender2 = "man";
                        }
                        else
                        {
                            gender2 = "woman";
                        }
                        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                        // [2]把对话框关闭
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String url = HttpUtilsHttpURLConnection.BASE_URL + "/Complete";
                                Map<String, String> params = new HashMap<String, String>();
                                //向map赋值
                                params.put("name", username);
                                params.put("phone", phone);
                                params.put("gender", gender2);
                                params.put("physique",physique1);
                                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                                System.out.println("结果是" + result);
                                if(gender2.equals("man"))
                                {
                                    gender = 1;
                                    gender1 = "男";
                                }
                                else
                                    {
                                    gender = 2;
                                    gender1 ="女";
                                }
                                //
                            }
                        }).start();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        values.put("gender", gender);
                        SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                        db2.update("user", values, "username = ?",new String[]{username} );
                        genderInText.setText(gender1);
                        dialog.dismiss();
                        System.out.println("点击了取消 ");
                    }
                });
                // 最后一步 一定要记得 和Toast 一样 show出来
                builder.show();
            }
        });
        //下属两行用于设置antivity返回
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }
    //重写这个方法就可以实现toolbar的返回，目前尚未看懂
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //动态申请权限
    private void requestPermission() {
        // 当API大于 23 （M表示23）时，才动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(linearinme.this,CAMERA_PERMISSIONS,CAMERA_PERMISSIONS_CODE);
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
                            Toast.makeText(linearinme.this, "请求权限被拒绝", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }else{
                    Toast.makeText(linearinme.this, "已授权", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    //弹出提示框
    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("设置头像需要相机和读写权限，是否去设置？")
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
                    touxiang.setImageURI(uri);
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
                        touxiang.setImageURI(originalUri);

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