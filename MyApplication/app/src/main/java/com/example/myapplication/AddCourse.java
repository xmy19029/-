package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.connect.Connect;

import org.json.JSONException;
import org.json.JSONObject;

public class AddCourse extends AppCompatActivity {
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        EditText editText = (EditText)findViewById(R.id.editText19);
        Button button = (Button)findViewById(R.id.button30);
        button.setOnClickListener(new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                //Toast.makeText(TeacherMainActivity.this,"",Toast.LENGTH_SHORT).show();
                org.json.JSONObject object= new JSONObject();
                try {
                    object.put("title", editText.getText());
                    object.put("pic","bbb");
                    object.put("tname","hyf9810");
                    object.put("tid",89757);
                    Connect conn = new Connect();
                    conn.sendReq2(handler2,1,"course/add","POST",String.valueOf(object),MainActivity.TOKEN);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
