package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {
    public static Activity instance;
    private SharedPreferences sp;
    private EditText name,password;
    private CheckBox rem,autoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
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
}
