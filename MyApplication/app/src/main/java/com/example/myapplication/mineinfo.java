package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mineinfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mineinfo);
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
    }
}
