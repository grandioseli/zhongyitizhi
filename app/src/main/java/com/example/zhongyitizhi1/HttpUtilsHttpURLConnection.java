package com.example.zhongyitizhi1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class
HttpUtilsHttpURLConnection {

    public static String BASE_URL= "http://47.102.222.34:8080/hello_world";
    /*
     * urlStr:网址
     * parms：提交数据
     * return:网页源码
     * */
    public static  String getContextByHttp(String urlStr, Map<String,String> parms){
        StringBuilder sb = new StringBuilder();
        try{
            //设定url对象
            URL url = new URL(urlStr);
            //创建连接
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //请求方式为post
            connection.setRequestMethod("POST");
            //设置从主机读取数据的超时时间
            connection.setReadTimeout(5000);
            //设置连接超时为5000ms
            connection.setConnectTimeout(5000);
            //设置使用url进行输入输出
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //设置此http连接是否实现重定向（相应3xx请求）
            connection.setInstanceFollowRedirects(true);
            //获取输出流对象设置此http进行输出
            OutputStream outputStream = connection.getOutputStream();
            //默认缓冲区大小构造字符缓冲输出流对象
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            //向缓冲区中写入指定的字符，也就是把参数中的map对象按照一定的规则组合成字符串
            writer.write(getStringFromOutput(parms));
            writer.flush();
            writer.close();
            outputStream.close();
            //如果响应成功
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                //创建输入流缓冲区
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String temp;
                //如果读取到数据了
                while((temp = reader.readLine()) != null){
                    sb.append(temp);
                }
                reader.close();
            }else{
                return "connection error:" + connection.getResponseCode();
            }

            connection.disconnect();
        }catch (Exception e){
            return e.toString();
        }
        return sb.toString();
    }

    /**
     * 将map转换成key1=value1&key2=value2的形式
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getStringFromOutput(Map<String,String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        //map.entrySet()返回了Map.Entry实例化后的对象集，因此下面的意思就是遍历map对象，把map中的全部键值对按照一定的规则添加到stringbuilder中
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(isFirst)
                isFirst = false;
            else
                sb.append("&");

            sb.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return sb.toString();
    }
}

