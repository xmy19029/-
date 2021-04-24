package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


public class onlineVideo extends AppCompatActivity {

    //String url = "http://cctvcnch5c.v.wscdns.com/live/cctv11_2/index.m3u8";
    //rtmp://39.105.21.114:1935/rtmplive/test
    String url = "rtmp://39.105.21.114:1935/rtmplive/";
    private VideoView mVideoView;
    private TextView textView;
    private List<String> data = new ArrayList<>();
    private long lastMessage = 0;
    Handler tmp = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    Handler refreshChat=new Handler();
    Handler chatPost = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            lastMessage = System.currentTimeMillis();
            String result = (String) msg.obj;
            JSONArray jsonArray = JSONArray.fromObject(result);
            for (int i = 0;i<jsonArray.size();i++) {
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(i).toString());
                textView.setText(textView.getText().toString() + jsonObject.getString("username") + " : "+jsonObject.getString("content") + "\n");
            }
        }
    };
    Runnable runChat=new Runnable(){
        @Override
        public void run() {
            Connect conn = new Connect();
            long now = System.currentTimeMillis();
            conn.sendReq2(chatPost,1,"comment/get?idRoom="+getIntent().getStringExtra("pwd")+"&&time="+lastMessage,"GET",null,MainActivity.TOKEN);
            refreshChat.postDelayed(this, 1000);
        }
    };
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_online_video);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        Window _window = getWindow();
        WindowManager.LayoutParams params = _window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        _window.setAttributes(params);
        mVideoView = findViewById(R.id.surface_view);
        textView = findViewById(R.id.textView75);

        /*
         * Alternatively,for streaming media you can use
         * mVideoView.setVideoURI(Uri.parse(URLstring));
         */
        System.out.println("!!"+url+getIntent().getStringExtra("pwd"));
        mVideoView.setVideoPath(url+getIntent().getStringExtra("pwd"));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
        getWatcher();
        lastMessage = 0;
        refreshChat.postDelayed(runChat,1000);
        Button button = (Button)findViewById(R.id.button32);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText20);
                Connect conn = new Connect();
                JSONObject object= new JSONObject();
                try {
                    object.put("time", System.currentTimeMillis());
                    object.put("username",getIntent().getStringExtra("usr"));
                    object.put("content",editText.getText().toString());
                    object.put("idRoom",getIntent().getStringExtra("pwd"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                conn.sendReq2(tmp,1,"comment/add","POST",String.valueOf(object),MainActivity.TOKEN);

            }
        });

    }
    private void getWatcher(){

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                data.clear();
                String result = (String) msg.obj;
                JSONArray jsonArray = JSONArray.fromObject(result);
                for (int i = 0;i<jsonArray.size();i++) {
                    net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(i).toString());
                    data.add((String)jsonObject.get("username"));
                }
                initList();
                data.add(getIntent().getStringExtra("usr"));
                ListView listView = (ListView)findViewById(R.id.watcherView);
                ((ArrayAdapter<String>)listView.getAdapter()).notifyDataSetChanged();
                Connect conn2 = new Connect();
                conn2.sendReq2(tmp,1,"watcher/add?idRoom="+getIntent().getStringExtra("pwd")+"&&username="+getIntent().getStringExtra("usr"),"POST",null,MainActivity.TOKEN);
            }
        };
        Connect conn = new Connect();
        conn.sendReq2(handler,1,"watcher/get?idRoom="+getIntent().getStringExtra("pwd"),"GET",null,MainActivity.TOKEN);
    }
    private void refresh(){
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                data.clear();
                String result = (String) msg.obj;
                JSONArray jsonArray = JSONArray.fromObject(result);
                for (int i = 0;i<jsonArray.size();i++) {
                    net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(i).toString());
                    data.add((String)jsonObject.get("username"));
                }
                ListView listView = (ListView)findViewById(R.id.watcherView);
                ((ArrayAdapter<String>)listView.getAdapter()).notifyDataSetChanged();
            }
        };
        Connect conn = new Connect();
        conn.sendReq2(handler,1,"watcher/get?idRoom="+getIntent().getStringExtra("pwd"),"GET",null,MainActivity.TOKEN);
    }
    private void initList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(onlineVideo.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView)findViewById(R.id.watcherView);
        listView.setAdapter(adapter);
        Handler handler=new Handler();
        Runnable runnable=new Runnable(){
            @Override
            public void run() {
                refresh();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Connect conn = new Connect();
        conn.sendReq2(tmp,1,"watcher/delWatcher?idRoom="+getIntent().getStringExtra("pwd")+"&&username="+getIntent().getStringExtra("usr"),"GET",null,MainActivity.TOKEN);
        refreshChat.removeCallbacks(runChat);
        // exitApplication();
    }
}
