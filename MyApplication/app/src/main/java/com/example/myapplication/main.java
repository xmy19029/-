package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class main extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MainActivity.instance.finish();
        ImageButton but1=(ImageButton) findViewById(R.id.imageButton0);
        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(main.this,note.class);
                startActivity(intent);
            }
        });
        ImageButton but2=(ImageButton) findViewById(R.id.imageButton2);
        but2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(main.this,luntan.class);
                startActivity(intent);
            }
        });
        ImageButton but3=(ImageButton) findViewById(R.id.imageButton3);
        but3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(main.this,mineinfo.class);
                startActivity(intent);
            }
        });
        ImageButton but4=(ImageButton) findViewById(R.id.imageButton4);
        but4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(main.this,fenxi.class);
                startActivity(intent);
            }
        });
        ImageButton but5=(ImageButton) findViewById(R.id.imageButton5);
        but5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(main.this,examing.class);
                startActivity(intent);
            }
        });
    }
}
