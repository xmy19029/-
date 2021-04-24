package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class OnAirAccess extends AppCompatActivity {
    private List<String> titles;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            JSONArray jsonArray = JSONArray.fromObject(result);
            titles = new ArrayList<>();
            for (int i = 0;i<jsonArray.size();i++) {
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(i).toString());
                titles.add((String)jsonObject.get("title") + " by "+(String)jsonObject.get("teacherName"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(OnAirAccess.this,android.R.layout.simple_list_item_1,titles);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            Spinner spinner = findViewById(R.id.spinner);

            spinner.setAdapter(adapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_air_access);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        Connect conn = new Connect();
        conn.sendReq2(handler,1,"Room/get","GET",null,MainActivity.TOKEN);
        Button button = findViewById(R.id.button24);
        EditText pwd = findViewById(R.id.editText17);
        EditText usr = findViewById(R.id.editText18);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OnAirAccess.this,onlineVideo.class);
                intent.putExtra("pwd",pwd.getText().toString());
                intent.putExtra("usr",usr.getText().toString());
                startActivity(intent);

            }
        });
    }
}
