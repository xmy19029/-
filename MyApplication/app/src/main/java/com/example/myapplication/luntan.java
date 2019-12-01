package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class luntan extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luntan);
        Button but=(Button) findViewById(R.id.button16);
        but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(luntan.this,tiezi.class);
                startActivity(intent);
            }
        });
        Button but1=(Button) findViewById(R.id.button22);
        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(luntan.this,fatie.class);
                startActivity(intent);
            }
        });
    }
}
