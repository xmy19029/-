package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button but=(Button) findViewById(R.id.button2);
        but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,zhuce.class);
                startActivity(intent);
            }
        });
        Button but1=(Button) findViewById(R.id.button);
        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Spinner spinner=(Spinner) findViewById(R.id.spinner);
                String choice=spinner.getSelectedItem().toString();
                if (choice.equals("学生")){
                    Intent intent=new Intent(MainActivity.this,main.class);
                    startActivity(intent);
                }else if(choice.equals("老师")){
                    Intent intent=new Intent(MainActivity.this,TeacherMainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
