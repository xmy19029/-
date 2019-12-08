package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        MainActivity.instance.finish();
        ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                //Toast.makeText(TeacherMainActivity.this,"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherMainActivity.this,TeacherQuestionActivity.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                //Toast.makeText(TeacherMainActivity.this,"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherMainActivity.this,ForumMainActivity.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                //Toast.makeText(TeacherMainActivity.this,"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherMainActivity.this,TeacherScoreActivity.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton4 = (ImageButton)findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener( ){
           @Override
           public void onClick(View v) {
               //Toast.makeText(TeacherMainActivity.this,"进入账户管理",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(TeacherMainActivity.this,mineinfo.class);
               startActivity(intent);
           }
        });
    }
}
