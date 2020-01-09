package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.bean.Posts;
import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class ForumMainActivity extends AppCompatActivity {
    List<Posts> posts = new ArrayList<>();
    String str = "";
    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return posts.size();
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
            Posts post = posts.get(position);
            View view;
            if (convertView == null){
                view = View.inflate(ForumMainActivity.this,R.layout.forum_item,null);
            } else {
                view = convertView;
            }
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(R.mipmap.account);
            TextView textView = view.findViewById(R.id.text);
            textView.setText(post.getTitle());
            return view;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ListView listView = (ListView) findViewById(R.id.listview);
            String result = (String) msg.obj;
            System.out.println(result);
            JSONArray jsonArray = JSONArray.fromObject(result);
            for (int i = 0;i<jsonArray.size();i++) {
                JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i).toString());
                Posts post = new Posts();
                post.setPid((Integer)jsonObject.get("pid"));
                post.setPoster((String)jsonObject.get("poster"));
                post.setTitle((String)jsonObject.get("title"));
                post.setText((String)jsonObject.get("text"));
                post.setLastReplyNum((Integer)jsonObject.get("lastReplyNum"));
                System.out.println(post);
                if(post.getTitle().contains(str))
                    posts.add(post);
            }
            final Adapter adapter = new Adapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Posts post = posts.get(position);
                    Intent intent = new Intent(ForumMainActivity.this,PostShowActivity.class);
                    intent.putExtra("post",post);
                    startActivity(intent);
                }
            });

        }
    };
    private void init(){
        Connect conn = new Connect();
        conn.sendReq(handler,1,"posts/getAll","GET",null,MainActivity.TOKEN);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);
        str = getIntent().getStringExtra("search_info");
        init();
        Button btn_search = (Button)findViewById(R.id.button_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumMainActivity.this,ForumMainActivity.class);
                String search_info = ((SearchView)findViewById (R.id.searchView)).getQuery().toString() ;
                intent.putExtra("search_info",search_info);
                startActivity(intent);
            }
        });
        Button btn_new = (Button)findViewById(R.id.button_new);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumMainActivity.this,NewPostActivity.class);
                startActivity(intent);
            }
        });
    }
}
