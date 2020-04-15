package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.bean.Posts;
import com.example.myapplication.bean.Replys;
import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostShowActivity extends AppCompatActivity {
    Posts post;
    private SharedPreferences sp;
    List<Replys> replys = new ArrayList<>();
    String replyname = "";
    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return replys.size();
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
            Replys reply = replys.get(position);
            View view;
            if (convertView == null){
                view = View.inflate(PostShowActivity.this,R.layout.reply_item,null);
            } else {
                view = convertView;
            }
            TextView textView = view.findViewById(R.id.text);
            if(reply.getReplyTo()==-1)
                textView.setText(reply.getUsername()+reply.getText());
            else{
                Connect conn = new Connect();
                conn.sendReq(handler3,1,"reply/getOne?id="+reply.getPostId(),"POST",null,MainActivity.TOKEN);
                textView.setText(reply.getUsername()+"回复"+replyname+":"+reply.getText());
            }
            return view;
        }
    }
    public void alert_edit(View view){
        final EditText et = new EditText(this);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        new AlertDialog.Builder(this).setTitle("请输入回复内容")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            org.json.JSONObject object= new JSONObject();
                            //sp = this.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                            object.put("postId",post.getPid());
                            object.put("username",sp.getString("name",""));
                            object.put("text","回复本帖:"+et.getText().toString());
                            object.put("replyTo",-1);
                            Connect conn = new Connect();
                            conn.sendReq(handler2,1,"reply/addOne","POST",String.valueOf(object),MainActivity.TOKEN);
                            Toast.makeText(getApplicationContext(), "回复成功",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton("取消",null).show();
    }
    public void alert_edit2(View view,String username){
        final EditText et = new EditText(this);
        final String user = username;
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        new AlertDialog.Builder(this).setTitle("请输入回复内容")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            org.json.JSONObject object= new JSONObject();
                            //sp = this.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                            object.put("postId",post.getPid());
                            object.put("username",sp.getString("name",""));
                            object.put("text","回复"+user+":"+et.getText().toString());
                            object.put("replyTo",-1);
                            Connect conn = new Connect();
                            conn.sendReq(handler2,1,"reply/addOne","POST",String.valueOf(object),MainActivity.TOKEN);
                            Toast.makeText(getApplicationContext(), "回复成功",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton("取消",null).show();
    }
    private Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            System.out.println("resultreply:"+result);
//            JSONArray jsonArray = JSONArray.fromObject(result);
//            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(0).toString());
            replyname = "plm";
        }
    };

    private  Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(PostShowActivity.this,PostShowActivity.class);
            intent.putExtra("post",post);
            startActivity(intent);
            finish();
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ListView listView = (ListView) findViewById(R.id.listview);
            String result = (String) msg.obj;
            System.out.println("result:"+result);
            JSONArray jsonArray = JSONArray.fromObject(result);
            for (int i = 0;i<jsonArray.size();i++) {
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(i).toString());
                Replys reply = new Replys();
                reply.setPostId((Integer)jsonObject.get("postId"));
                reply.setUsername((String)jsonObject.get("username"));
                reply.setText((String)jsonObject.get("text"));
                reply.setReplyTo((Integer)jsonObject.get("replyTo"));
                System.out.println(reply);
                replys.add(reply);
            }
            final Adapter adapter = new Adapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Connect conn = new Connect();
                    alert_edit2(view,replys.get(position).getUsername());
                }
            });

        }
    };
    private void init(){
        Connect conn = new Connect();
        conn.sendReq(handler,1,"reply/getByPost?pid="+post.getPid(),"GET",null,MainActivity.TOKEN);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show);
        post = (Posts)getIntent().getSerializableExtra("post");
        TextView title = (TextView) findViewById(R.id.textViewTitle);
        TextView cotent = (TextView) findViewById(R.id.textViewContent);
        title.setText(post.getTitle());
        cotent.setText(post.getText());
        init();
    }
}
