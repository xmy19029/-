package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.bean.Video;
import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class courseDetail extends AppCompatActivity {
    List<Video> videos = new ArrayList<>();
    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return videos.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Video video = videos.get(position);
            View view;
            if (convertView == null){
                view = View.inflate(courseDetail.this,R.layout.video_item,null);
            } else {
                view = convertView;
            }
            TextView textView = view.findViewById(R.id.videoTitle);
            textView.setText(video.getTitle());
            return view;
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ListView listView = (ListView) findViewById(R.id.videoListView);
            String result = (String) msg.obj;
            System.out.println(result);
            JSONArray jsonArray = JSONArray.fromObject(result);
            for (int i = 0;i<jsonArray.size();i++) {
                JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i).toString());
                Video video = new Video();
                video.setTitle((String)jsonObject.get("title"));
                video.setFile((String)jsonObject.get("file"));
                video.setIdvideo(jsonObject.getInt("idvideo"));
                videos.add(video);
            }
            final Adapter adapter = new Adapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent intent = new Intent(courseDetail.this,VideoPlayer.class);
                    Intent intent = new Intent(courseDetail.this,DMTest.class);
                    intent.putExtra("url",videos.get(position).getFile());
                    intent.putExtra("id",videos.get(position).getIdvideo());
                    startActivity(intent);
                }
            });
        }
    };
    private void init(){
        Connect conn = new Connect();
        conn.sendReq2(handler,1,"video/getByCourse?idcourse="+getIntent().getIntExtra("idcourse",1),"GET",null,MainActivity.TOKEN);
    }
    public int  getResource(String imageName){
        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        init();
        TextView textView = findViewById(R.id.textView65);
        textView.setText(getIntent().getStringExtra("courseTitle"));
        TextView textView2 = findViewById(R.id.textView66);
        textView2.setText(getIntent().getStringExtra("teacherName"));
        ImageView imageView = findViewById(R.id.imageView10);
        imageView.setImageResource(getResource(getIntent().getStringExtra("pic")));
    }
}
