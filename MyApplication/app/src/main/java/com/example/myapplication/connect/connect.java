package com.example.myapplication.connect;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class connect {
    private static final String[] HTTP_METHOD = {"GET", "POST", "DELETE", "PUT"};
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
    /**
     * 发送HTTP请求
     * @param url 请求的url
     * @param httpMethod 请求方法
     * @param msg 要写入的信息
     * @param token 请求头
     * @return
     */
    public static String sendReq(String url, String httpMethod, String msg,String token) {
        if (url == null || url.length() == 0) {
            return null;
        }
        if (!isContain(httpMethod)) {
            return null;
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
            huc.setRequestMethod("GET");
            // 设置是否使用缓存
            huc.setUseCaches(false);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            huc.setInstanceFollowRedirects(true);
            // 设置超时时间
            huc.setConnectTimeout(3000);
            // 设置请求头"Content-Type":"application/json"
            //huc.setRequestProperty("Content-Type", "application/json");

            // 设置其他请求头
            if (token==null||token.length()==0){

            }else{
                huc.setRequestProperty("token",token);
            }
            // 与资源建立通信连接
            System.out.println("开始connect");
            huc.connect();
            if (msg != null&&msg.length()!=0) {
                // 将信息写入到输出流 ,msg的格式为json串
                writeMsgToStream(huc.getOutputStream(), msg);
            }
            System.out.println(huc.getResponseCode());
            if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return getMsgFromStream(huc.getInputStream());
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
        return null;
    }
}
