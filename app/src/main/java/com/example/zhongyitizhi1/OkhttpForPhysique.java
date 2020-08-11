package com.example.zhongyitizhi1;

import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class
OkhttpForPhysique {
//请求体质的url
    public static String BASE_URL= "http://120.26.163.91:500/upload";
    /*
     * urlStr:网址
     * parms：提交数据
     * return:网页源码
     * */
    //简单而言就是向指定url上传图片文件，接受返回字符串
    public static String uploadImage(String url, String imagePath) throws IOException, JSONException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d("imagePath", imagePath);
        File file = new File(imagePath);
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imagePath, image)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String aa = response.body().string();
        return aa;
    }
}

