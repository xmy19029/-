package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OnAirOpen extends AppCompatActivity {
    private TextView stat;
    private TextView pwd;
    private EditText title;
    private Button open,close;
    private String usr,newpwd;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            JSONArray jsonArray = JSONArray.fromObject(result);
            String user_name = usr;
            boolean isOpen = false;
            for (int i = 0;i<jsonArray.size();i++) {
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(i).toString());
                if(jsonObject.getString("idteacher").equals(user_name)){
                    isOpen = true;
                    setOpen(jsonObject.getString("title"),jsonObject.getString("password"));
                    break;
                }
            }
            if(!isOpen) setClose();
            init();
        }
    };

    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (!result.equals("1")) return;;
            setOpen(title.getText().toString(),newpwd);
        }
    };
    private Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (!result.equals("1")) return;;
            setClose();
        }
    };
    private Handler handler4 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_air_open);
        usr=getIntent().getStringExtra("user_name");
        stat = findViewById(R.id.textView72);
        pwd = findViewById(R.id.textView73);
        title = findViewById(R.id.editText16);
        open = findViewById(R.id.button29);
        close = findViewById(R.id.button31);
        Connect conn = new Connect();
        conn.sendReq2(handler,1,"Room/get","GET",null,MainActivity.TOKEN);

    }

    private void setClose(){
        stat.setText("当前状态：未开播");
        pwd.setText("（生成口令）");
        title.setEnabled(true);
        open.setEnabled(true);
        close.setEnabled(false);
    }
    private void setOpen(String caption,String password){
        stat.setText("当前状态：已开播");
        pwd.setText(password);
        title.setText(caption);
        title.setEnabled(false);
        open.setEnabled(false);
        close.setEnabled(true);
    }
    private void init(){
        findViewById(R.id.button29).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                org.json.JSONObject object= new JSONObject();
                try {
                    newpwd = System.currentTimeMillis()%1000000+"@"+usr;
                    object.put("idteacher", usr);
                    object.put("title",title.getText().toString());
                    object.put("password",newpwd);
                    object.put("teacherName",usr);
                    Connect conn = new Connect();
                    conn.sendReq2(handler2, 1, "Room/add", "POST", String.valueOf(object), MainActivity.TOKEN);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.button31).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                org.json.JSONObject object= new JSONObject();
                    Connect conn = new Connect();
                    conn.sendReq2(handler3, 1, "Room/del?idteacher="+usr, "GET", null, MainActivity.TOKEN);
                    Connect connect = new Connect();
                connect.sendReq2(handler4, 1, "watcher/delRoom?idRoom="+pwd.getText().toString(), "GET", null, MainActivity.TOKEN);
            }
        });
    }
}
