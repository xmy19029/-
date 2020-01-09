package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class addnote extends Activity {
    private EditText title,content;
    private SharedPreferences sp;
    private String whose;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnote);
        whose=getIntent().getStringExtra("whose");
        System.out.println("addnote---"+whose);
        sp=getSharedPreferences(whose, Context.MODE_PRIVATE);
        title=findViewById(R.id.editText10);
        content=findViewById(R.id.editText11);
        Button button=findViewById(R.id.button10);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                JSONObject Note=new JSONObject();
                try {
                    Note.put("title",title.getText().toString());
                    Note.put("content",content.getText().toString());
                    int num=sp.getInt("num",0)+1;
                    //Note.put("id",num);
                    sp.edit().putInt("num",num).commit();
                    sp.edit().putString(String.valueOf(num),Note.toString()).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(addnote.this,note.class);
                intent.putExtra("whose",whose);
                startActivity(intent);
                finish();
            }
        });
        Button button1=findViewById(R.id.button11);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(addnote.this,note.class);
                intent.putExtra("whose",whose);
                startActivity(intent);
                finish();
            }
        });
    }
}
