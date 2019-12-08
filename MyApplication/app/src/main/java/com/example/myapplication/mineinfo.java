package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mineinfo extends Activity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mineinfo);
        sp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Button button=(Button) findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(mineinfo.this,information.class);
                startActivity(intent);
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
            }
        });
    }
}
