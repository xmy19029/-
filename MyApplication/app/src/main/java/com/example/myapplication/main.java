package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class main extends Activity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        MainActivity.instance.finish();
        ImageButton but1=(ImageButton) findViewById(R.id.imageButton0);
        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(main.this,note.class);
                intent.putExtra("whose",sp.getString("name",""));
                startActivity(intent);
            }
        });
        ImageButton but2=(ImageButton) findViewById(R.id.imageButton2);
        but2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(main.this,ForumMainActivity.class);
                intent.putExtra("search_info","");
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
                intent.putExtra("username",sp.getString("name",""));
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
        ImageButton but6=(ImageButton) findViewById(R.id.imageButton);
        but6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(main.this,"敬请期待！",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
