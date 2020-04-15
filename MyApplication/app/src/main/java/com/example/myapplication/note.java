package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class note extends Activity {
    private int index=0;
    private String whose;
    private TextView t1,t2,t3,t4,t5;
    private SharedPreferences sp;
    private void upUI(){
        int num=sp.getInt("num",0);//总数
        num=num-(index)*5;
        int min=num;
        if(num>5) min=5;
        if(index>0){
            onCreate(null);
        }
        for(int i=0;i<min;i++){
            String title=sp.getString(String.valueOf((index*5)+i+1),"");
            if(title!=null&&title.length()!=0){
                JSONObject js= null;
                try {
                    js = new JSONObject(title);
                    switch (i){
                        case 0:t1.setText(js.getString("title"));
                            break;
                        case 1:t2.setText(js.getString("title"));
                            break;
                        case 2:t3.setText(js.getString("title"));
                            break;
                        case 3:t4.setText(js.getString("title"));
                            break;
                        case 4:t5.setText(js.getString("title"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        whose=getIntent().getStringExtra("whose");
        System.out.println("note---"+whose);
        sp=getSharedPreferences(whose, Context.MODE_PRIVATE);
        System.out.println(sp.getAll());
        //sp.edit().clear().commit();
        t1=findViewById(R.id.textView26);
        t2=findViewById(R.id.textView27);
        t3=findViewById(R.id.textView28);
        t4=findViewById(R.id.textView29);
        t5=findViewById(R.id.textView30);
        if(index==0){
            upUI();
        }
        Button button=(Button) findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(note.this,addnote.class);
                intent.putExtra("whose",whose);
                startActivity(intent);
                finish();
            }
        });
        Button buttonUp=(Button) findViewById(R.id.button28);
        buttonUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(index==0){
                    Toast.makeText(note.this,"已经是第一页",
                            Toast.LENGTH_LONG).show();
                }else{
                    index--;
                    upUI();
                }
            }
        });
        Button buttonDown=(Button) findViewById(R.id.button27);
        buttonDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(sp.getInt("num",0)>( (index+1)*5 )){
                    index++;
                    upUI();
                }else Toast.makeText(note.this,"已经是最后一页",
                        Toast.LENGTH_LONG).show();
            }
        });
        View view=findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (index*5+1)> sp.getInt("num",0)){

                }else {
                    Intent intent=new Intent(note.this,onenote.class);
                    intent.putExtra("id",index*5+1);
                    intent.putExtra("whose",whose);
                    startActivity(intent);
                }
            }
        });
        View view1=findViewById(R.id.view2);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (index*5+2)> sp.getInt("num",0)){

                }else {
                    Intent intent=new Intent(note.this,onenote.class);
                    intent.putExtra("id",index*5+2);
                    intent.putExtra("whose",whose);
                    startActivity(intent);
                }
            }
        });
        View view2=findViewById(R.id.view3);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (index*5+3)> sp.getInt("num",0)){

                }else {
                    Intent intent=new Intent(note.this,onenote.class);
                    intent.putExtra("id",index*5+3);
                    intent.putExtra("whose",whose);
                    startActivity(intent);
                }
            }
        });
        View view3=findViewById(R.id.view4);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (index*5+4)> sp.getInt("num",0)){

                }else {
                    Intent intent=new Intent(note.this,onenote.class);
                    intent.putExtra("id",index*5+4);
                    intent.putExtra("whose",whose);
                    startActivity(intent);
                }
            }
        });
        View view4=findViewById(R.id.view5);
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (index*6)> sp.getInt("num",0)){

                }else {
                    Intent intent=new Intent(note.this,onenote.class);
                    intent.putExtra("id",index*6);
                    intent.putExtra("whose",whose);
                    startActivity(intent);
                }
            }
        });
    }
}
