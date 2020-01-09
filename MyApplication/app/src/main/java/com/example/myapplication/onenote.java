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

public class onenote extends Activity {
    private int id;
    private String whose;
    private SharedPreferences sp;
    private TextView title,zhengwen;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onenote);
        whose=getIntent().getStringExtra("whose");
        System.out.println("onenote---"+whose);
        id=getIntent().getIntExtra("id",1);
        sp=getSharedPreferences(whose, Context.MODE_PRIVATE);
        title=findViewById(R.id.textView60);
        zhengwen=findViewById(R.id.textView62);
        String content=sp.getString(String.valueOf(id),"");
        if(content!=null&&content.length()!=0){
            JSONObject neirong= null;
            try {
                neirong = new JSONObject(content);
                title.setText(neirong.getString("title"));
                zhengwen.setText(neirong.getString("content"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Button delete=(Button) findViewById(R.id.button25);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sp.edit().remove(String.valueOf(id)).commit();
                int num=sp.getInt("num",0);
                sp.edit().putInt("num",num-1).commit();
                for(int i=id+1;i<=num;i++){
                    String content=sp.getString(String.valueOf(i),"");
                    sp.edit().putString(String.valueOf(i-1),content).commit();
                }
                sp.edit().remove(String.valueOf(num)).commit();
                Intent intent=new Intent(onenote.this,note.class);
                intent.putExtra("whose",whose);
                startActivity(intent);
                finish();
            }
        });
        Button close=(Button) findViewById(R.id.button23);
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(onenote.this,note.class);
                intent.putExtra("whose",whose);
                startActivity(intent);
                finish();
            }
        });
    }
}
