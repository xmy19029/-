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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.connect.Connect;

public class changemima extends Activity {
    private SharedPreferences sp;
    private int shenfen;
    private TextView name,mine,e_mine;
    private EditText e_name;
    private Button add,delete,querry;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.arg1==1){
                int code=Integer.valueOf(msg.obj.toString());
                if(code==1){
                    Toast.makeText(changemima.this,"操作成功！",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(changemima.this,"操作失败！",
                            Toast.LENGTH_LONG).show();
                }
            }else if(msg.arg1==2) {
                onCreate(null);
                e_mine.setText(msg.obj.toString());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changemima);
        name=findViewById(R.id.textView50);
        mine=findViewById(R.id.textView51);
        e_name=findViewById(R.id.editText9);
        e_mine=findViewById(R.id.editText7);
        add=findViewById(R.id.button18);
        delete=findViewById(R.id.button17);
        querry=findViewById(R.id.button5);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        shenfen=sp.getInt("shenfen",0);
        if(shenfen==1){
            name.setText("老师名");
            mine.setText("我的老师");
        }else if(shenfen==2){
            name.setText("学生名");
            mine.setText("我的学生");
        }
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String q_name=e_name.getText().toString();
                if(shenfen==1){
                    Connect connect=new Connect();
                    connect.sendReq(handler,1,"relations/addOne?student="+
                            sp.getString("name","")+"&teacher="+q_name,"POST",null,MainActivity.TOKEN);
                }else if(shenfen==2){
                    Connect connect=new Connect();
                    connect.sendReq(handler,1,"relations/addOne?student="+
                            q_name+"&teacher="+sp.getString("name",""),"POST",null,MainActivity.TOKEN);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String q_name=e_name.getText().toString();
                if(shenfen==1){
                    Connect connect=new Connect();
                    connect.sendReq(handler,1,"relations/delOne?student="+
                            sp.getString("name","")+"&teacher="+q_name,"DELETE",null,MainActivity.TOKEN);
                }else if(shenfen==2){
                    Connect connect=new Connect();
                    connect.sendReq(handler,1,"relations/delOne?student="+
                            q_name+"&teacher="+sp.getString("name",""),"DELETE",null,MainActivity.TOKEN);
                }
            }
        });
        querry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shenfen==1){
                    Connect connect=new Connect();
                    connect.sendReq(handler,2,"relations/getTeacherToStudent?student="+sp.getString("name",""),"GET",null,MainActivity.TOKEN);
                }else if(shenfen==2){
                    Connect connect=new Connect();
                    connect.sendReq(handler,2,"relations/getStudentToTeacher?teacher="+sp.getString("name",""),"GET",null,MainActivity.TOKEN);
                }
            }
        });
    }
}
