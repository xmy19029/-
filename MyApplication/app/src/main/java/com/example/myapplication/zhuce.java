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
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.example.myapplication.connect.Connect;
import com.example.myapplication.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class zhuce extends Activity {
    private int id,change;
    private String name,college,major;
    private SharedPreferences sp;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(zhuce.this, mineinfo.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);
        change=getIntent().getIntExtra("change",0);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Button button=findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText id_e=findViewById(R.id.editText3);
                EditText name_e=findViewById(R.id.editText4);
                EditText college_e=findViewById(R.id.editText5);
                EditText major_e=findViewById(R.id.editText6);
                id=Integer.parseInt(id_e.getEditableText().toString().trim());
                name=name_e.getText().toString();
                major=major_e.getText().toString();
                college=college_e.getText().toString();
                JSONObject information=new JSONObject();
                try {
                    information.put("username",sp.getString("name",""));
                    information.put("name",name);
                    information.put("academy",college);
                    information.put("major",major);
                    information.put("grade","school");
                    String url="";
                    if(sp.getInt("shenfen",0)==1){
                        information.put("sid",id);
                        Connect connect=new Connect();
                        if(change==0){
                            url="student/addOne";
                        }else if(change==1){
                            url="student/updOne?username="+sp.getString("name","");
                        }
                        connect.sendReq(handler,1,url,"POST",information.toString(),MainActivity.TOKEN);
                    }else if(sp.getInt("shenfen",0)==2){
                        information.put("tid",id);
                        Connect connect=new Connect();
                        if(change==0){
                            url="teacher/addOne";
                        }else if(change==1){
                            url="teacher/updOne?username="+sp.getString("name","");
                        }
                        connect.sendReq(handler,1,url,"POST",information.toString(),MainActivity.TOKEN);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Button fangqi=findViewById(R.id.button4);
        fangqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
