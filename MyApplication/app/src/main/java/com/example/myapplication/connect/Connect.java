package com.example.myapplication.connect;

import android.app.Activity;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Connect {
    private static final String[] HTTP_METHOD = {"GET", "POST", "DELETE", "PUT"};
    String url;
    String httpMethod;
    String msg;
    String token;
    String result;
    Handler handler = null;
    int what;
    /**
     * 读取输入流
     * @param is
     * @return
     */
    private static String getMsgFromStream(InputStream is) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String content = null;
            while ((content = br.readLine()) != null) {
                sb.append(content);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private static void writeMsgToStream(OutputStream os, String msg) {
        try {
            os.write(msg.getBytes("UTF-8"));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 判断请求方法是否为get、post、delete、put
     * @param httpMethod
     * @return
     */
    private static boolean isContain(String httpMethod) {
        if (httpMethod == null || httpMethod.length() == 0) {
            return false;
        }
        for (String str : HTTP_METHOD) {
            if (str.equals(httpMethod)) {
                return true;
            }
        }
        return false;
    }
    public void goto_handler(String result){
        Message message = Message.obtain();
        message.obj = result;
        message.arg1=this.what;
        handler.sendMessage(message);
    }
    public String sendReq(Handler handler,int what,String URL, String HttpMethod, String MSG,String TOKEN) {
        this.url = "http://39.105.21.114:11452/api/v1/"+URL;
        this.httpMethod = HttpMethod;
        this.msg = MSG;
        this.token = TOKEN;
        this.handler = handler;
        this.what=what;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (url == null || url.length() == 0) {
                    result = null;
                    return ;
                }
                if (!isContain(httpMethod)) {
                    result = null;
                    return ;
                }
                HttpURLConnection huc = null;
                try {
                    URL postUrl = new URL(url);
                    huc = (HttpURLConnection) postUrl.openConnection();
                    // 设置从URLConnection读入，默认值为true
                    huc.setDoInput(true);
                    // 设置从URLConnection读入
                    huc.setDoOutput(true);
                    // 设置请求方法
                    huc.setRequestMethod(httpMethod);
                    //huc.setSSLSocketFactory(context.getSocketFactory());
                    // 设置是否使用缓存
                    huc.setUseCaches(false);
                    // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
                    huc.setInstanceFollowRedirects(true);
                    // 设置超时时间
                    huc.setConnectTimeout(3000);
                    // 设置请求头"Content-Type":"application/json"
                    huc.setRequestProperty("Content-Type", "application/json");
                    // 设置其他请求头
                    if (token==null||token.length()==0){

                    }else{
                        huc.setRequestProperty("Authorization",token);
                    }
                    // 与资源建立通信连接
                    huc.connect();
                    if (msg != null&&msg.length()!=0) {
                        // 将信息写入到输出流 ,msg的格式为json串
                        writeMsgToStream(huc.getOutputStream(), msg);
                    }
                    if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = getMsgFromStream(huc.getInputStream());
                        if(huc.getHeaderFields().containsKey("token")){
                            if(huc.getHeaderFields().get("token").size()!=0){
                                MainActivity.TOKEN=huc.getHeaderFields().get("token").get(0);
                            }
                        }
                        goto_handler(result);
                        System.out.println(result);
                        return ;
                    } else {
                        System.out.println(huc.getResponseCode() + ":" +huc.getResponseMessage());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (huc != null) {
                        huc.disconnect();
                    }
                }
                return ;
            }
        }).start();
        return result;
    }
    public String sendReq2(Handler handler,int what,String URL, String HttpMethod, String MSG,String TOKEN) {
        this.url = "http://39.105.21.114:12306/api/v1/"+URL;
        this.httpMethod = HttpMethod;
        this.msg = MSG;
        this.token = TOKEN;
        this.handler = handler;
        this.what=what;
        System.out.println("this is"+this.url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (url == null || url.length() == 0) {
                    result = null;
                    return ;
                }
                if (!isContain(httpMethod)) {
                    result = null;
                    return ;
                }
                HttpURLConnection huc = null;
                try {
                    URL postUrl = new URL(url);
                    huc = (HttpURLConnection) postUrl.openConnection();
                    // 设置从URLConnection读入，默认值为true
                    huc.setDoInput(true);
                    // 设置从URLConnection读入
                    huc.setDoOutput(true);
                    // 设置请求方法
                    huc.setRequestMethod(httpMethod);
                    //huc.setSSLSocketFactory(context.getSocketFactory());
                    // 设置是否使用缓存
                    huc.setUseCaches(false);
                    // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
                    huc.setInstanceFollowRedirects(true);
                    // 设置超时时间
                    huc.setConnectTimeout(3000);
                    // 设置请求头"Content-Type":"application/json"
                    huc.setRequestProperty("Content-Type", "application/json");
                    // 设置其他请求头
                    if (token==null||token.length()==0){

                    }else{
                        huc.setRequestProperty("Authorization",token);
                    }
                    // 与资源建立通信连接
                    huc.connect();
                    if (msg != null&&msg.length()!=0) {
                        // 将信息写入到输出流 ,msg的格式为json串
                        writeMsgToStream(huc.getOutputStream(), msg);
                    }
                    if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = getMsgFromStream(huc.getInputStream());
                        if(huc.getHeaderFields().containsKey("token")){
                            if(huc.getHeaderFields().get("token").size()!=0){
                                MainActivity.TOKEN=huc.getHeaderFields().get("token").get(0);
                            }
                        }
                        goto_handler(result);
                        System.out.println(result);
                        return ;
                    } else {
                        System.out.println(huc.getResponseCode() + ":" +huc.getResponseMessage());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (huc != null) {
                        huc.disconnect();
                    }
                }
                return ;
            }
        }).start();

        return result;
    }
    public String sendReq3(Handler handler,int what,String URL, String HttpMethod, String MSG,String TOKEN) {
        this.url = "http://39.105.21.114:12306/"+URL;
        this.httpMethod = HttpMethod;
        this.msg = MSG;
        this.token = TOKEN;
        this.handler = handler;
        this.what=what;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (url == null || url.length() == 0) {
                    result = null;
                    return ;
                }
                if (!isContain(httpMethod)) {
                    result = null;
                    return ;
                }
                HttpURLConnection huc = null;
                try {
                    URL postUrl = new URL(url);
                    huc = (HttpURLConnection) postUrl.openConnection();
                    // 设置从URLConnection读入，默认值为true
                    huc.setDoInput(true);
                    // 设置从URLConnection读入
                    huc.setDoOutput(true);
                    // 设置请求方法
                    huc.setRequestMethod(httpMethod);
                    //huc.setSSLSocketFactory(context.getSocketFactory());
                    // 设置是否使用缓存
                    huc.setUseCaches(false);
                    // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
                    huc.setInstanceFollowRedirects(true);
                    // 设置超时时间
                    huc.setConnectTimeout(3000);
                    // 设置请求头"Content-Type":"application/json"
                    huc.setRequestProperty("Content-Type", "application/json");
                    // 设置其他请求头
                    if (token==null||token.length()==0){

                    }else{
                        huc.setRequestProperty("Authorization",token);
                    }
                    // 与资源建立通信连接
                    huc.connect();
                    if (msg != null&&msg.length()!=0) {
                        // 将信息写入到输出流 ,msg的格式为json串
                        writeMsgToStream(huc.getOutputStream(), msg);
                    }
                    if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        result = getMsgFromStream(huc.getInputStream());
                        if(huc.getHeaderFields().containsKey("token")){
                            if(huc.getHeaderFields().get("token").size()!=0){
                                MainActivity.TOKEN=huc.getHeaderFields().get("token").get(0);
                            }
                        }
                        goto_handler(result);
                        System.out.println(result);
                        return ;
                    } else {
                        System.out.println(huc.getResponseCode() + ":" +huc.getResponseMessage());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (huc != null) {
                        huc.disconnect();
                    }
                }
                return ;
            }
        }).start();

        return result;
    }
}
