package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.connect.connect;
import com.example.myapplication.data.DBHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static Activity instance;
    private SharedPreferences sp;
    private EditText name,password;
    private CheckBox rem,autoLogin;
    private SQLiteDatabase database;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        helper=new DBHelper(this,"myDb.db",null,1);
        database=helper.getWritableDatabase();
        name=findViewById(R.id.editText2);
        password=findViewById(R.id.editText);
        rem=findViewById(R.id.radioButton);
        autoLogin=findViewById(R.id.radioButton6);
        sp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if(sp.getBoolean("rem",false)){
            rem.setChecked(true);
            name.setText(sp.getString("name",""));
            password.setText(sp.getString("password",""));
            if(sp.getBoolean("auto",false)){
                autoLogin.setChecked(true);
                if(sp.getBoolean("ifTeacher",false)){
                    Intent intent=new Intent(MainActivity.this,TeacherMainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(MainActivity.this,main.class);
                    startActivity(intent);
                }
            }
        }
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
                String u_name=name.getText().toString();
                String u_password=password.getText().toString();
                Spinner spinner=(Spinner) findViewById(R.id.spinner);
                String choice=spinner.getSelectedItem().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getwebinfo();
                    }
                }).start();
                if(true){//登陆成功
                    if(rem.isChecked()){
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("name",u_name);
                        editor.putString("password",u_password);
                        if (choice.equals("学生")){
                            editor.putBoolean("isTeacher",false);
                            editor.apply();
                            Intent intent=new Intent(MainActivity.this,main.class);
                            startActivity(intent);
                        }else if(choice.equals("老师")){
                            editor.putBoolean("isTeacher",true);
                            editor.apply();
                            Intent intent=new Intent(MainActivity.this,TeacherMainActivity.class);
                            startActivity(intent);
                        }
                    }
                    //页面跳转
                    Intent intent=new Intent(MainActivity.this,main.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"用户名或密码错误",
                    Toast.LENGTH_LONG).show();
                }

            }
        });
        rem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rem.isChecked()){
                    sp.edit().putBoolean("rem",true).commit();
                }else{
                    sp.edit().putBoolean("rem",false).commit();
                }
            }
        });
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(autoLogin.isChecked()){
                    sp.edit().putBoolean("auto",true).commit();
                }else{
                    sp.edit().putBoolean("auto",false).commit();
                }
            }
        });
    }
    private void getwebinfo() {
        try {
            //1,找水源--创建URL
            URL url = new URL("https://www.baidu.com/");//放网站
            //2,开水闸--openConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //3，建管道--InputStream
            InputStream inputStream = httpURLConnection.getInputStream();
            //4，建蓄水池蓄水-InputStreamReader
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            //5，水桶盛水--BufferedReader
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer buffer = new StringBuffer();
            String temp = null;

            while ((temp = bufferedReader.readLine()) != null) {
                //取水--如果不为空就一直取
                buffer.append(temp);
            }
            bufferedReader.close();//记得关闭
            reader.close();
            inputStream.close();
            System.out.println(buffer.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
