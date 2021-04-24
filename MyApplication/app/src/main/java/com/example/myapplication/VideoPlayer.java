package com.example.myapplication;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class VideoPlayer extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_player);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }

        bindViews();
    }

    private void bindViews() {
        videoView = findViewById(R.id.videoView);

        /**播放 res/raw 目录下的文件
         * android.resource:// ：前缀固定
         * com.example.administrator.helloworld：为当前类的所在的包路径，可以使用 String packageName = getPackageName(); 动态获取
         * R.raw.la_isla：最后接 res/raw 目录中的文件名
         * */

        //videoView.setVideoURI(Uri.parse("android.resource://com.example.myapplication/" + R.raw.ys));
        videoView.setVideoPath("http://39.105.21.114:12306/file/video/get?filename="+getIntent().getStringExtra("url"));
        //videoView.setVideoPath("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8");
        //videoView.setVideoPath("http://39.105.21.114:10001/rtmplive/test.m3u8");

        /**
         * 为 VideoView 视图设置媒体控制器，设置了之后就会自动由进度条、前进、后退等操作
         */
        videoView.setMediaController(new MediaController(this));

        /**视频准备完成时回调
         * */
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i("tag", "--------------视频准备完毕,可以进行播放.......");
            }
        });
        /**
         * 视频播放完成时回调
         */
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("tag", "------------------视频播放完毕..........");
                /**播放完成时，再次循环播放*/
                videoView.start();
            }
        });

        /**
         * 视频播放发送错误时回调
         */
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                System.out.println("fuck");
                Log.i("tag", "---------------------视频播放失败...........");
                videoView.start();
                return false;
            }
        });

        /**开始播放视频
         * */
        videoView.start();
    }

}
