package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.connect.Connect;

import org.json.JSONException;
import org.json.JSONObject;

public class mineinfo extends Activity {
    //public static int has=0;
    private SharedPreferences sp;
    private TextView t_name,t_id,t_college;
    private int done=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.obj!=null&&msg.obj.toString().length()!=0){
                //has=1;
                done=1;
                onCreate(null);
                JSONObject info= null;
                try {
                    info = new JSONObject(msg.obj.toString());
                    t_name.setText(info.getString("username"));
                    t_college.setText(info.getString("academy")+"    "+info.getString("major"));
                    if(msg.arg1==1){
                        t_id.setText(info.getInt("sid")+"    "+info.getString("name"));
                    }else if(msg.arg1==2){
                        t_id.setText(info.getInt("tid")+"    "+info.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                done=0;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mineinfo);
        sp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        t_name=findViewById(R.id.textView11);
        t_id=findViewById(R.id.textView);
        t_college=findViewById(R.id.textView12);
        if(done==0){
            t_name.setText(sp.getString("name",""));
            Connect connect=new Connect();
            if(sp.getInt("shenfen",0)==1){
                connect.sendReq(handler,1,"student/getOne?username="+sp.getString("name",""),"GET",null,MainActivity.TOKEN);
            }else if(sp.getInt("shenfen",0)==2){
                connect.sendReq(handler,2,"teacher/getOne?username="+sp.getString("name",""),"GET",null,MainActivity.TOKEN);
            }
        }
        Button button=(Button) findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(done==0){
                    Intent intent=new Intent(mineinfo.this,zhuce.class);
                    startActivity(intent);
                    //finish();
                }else if(done==1){
                    Toast.makeText(mineinfo.this,"已经完善！",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        Button button1=(Button) findViewById(R.id.button9);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(mineinfo.this,changemima.class);
                startActivity(intent);
            }
        });
        Button button2=findViewById(R.id.button8);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sp.edit().putBoolean("auto",false).commit();
                Intent intent=new Intent(mineinfo.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button button3=(Button) findViewById(R.id.button6);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(done==0){
                    Toast.makeText(mineinfo.this,"还未完善！",
                            Toast.LENGTH_LONG).show();
                }else if(done==1){
                    Intent intent=new Intent(mineinfo.this,zhuce.class);
                    intent.putExtra("change",1);
                    startActivity(intent);
                }
            }
        });
    }
}
