package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.connect.Connect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Activity instance;
    public static String ip_address = "http://39.105.21.114:11451/api/v1/";
    public static String TOKEN;
    private String u_name,u_password;
    private SharedPreferences sp;
    private EditText name, password;
    private CheckBox rem, autoLogin;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.arg1==1){
                if(msg.obj.equals("ROLE_STUDENT")){
                    sp.edit().putInt("shenfen",1).commit();
                    Intent intent = new Intent(MainActivity.this, main.class);
                    intent.putExtra("user_name",name.getText().toString());
                    startActivity(intent);
                }else if(msg.obj.equals("ROLE_TEACHER")){
                    sp.edit().putInt("shenfen",2).commit();
                    Intent intent = new Intent(MainActivity.this, TeacherMainActivity.class);
                    intent.putExtra("user_name",name.getText().toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"账号或密码错误！",
                            Toast.LENGTH_LONG).show();
                }
            }else if(msg.arg1==0&&Integer.valueOf(msg.obj.toString())==1){
                Toast.makeText(MainActivity.this,"注册成功！",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
    private void signIn(String n,String p){
        JSONObject info=new JSONObject();
        try {
            info.put("username",n);
            info.put("password",p);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Connect connect=new Connect();
        connect.sendReq(handler,1,"user/login","POST",info.toString(),null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        u_name=null;
        u_password=null;
        instance = this;
        name = findViewById(R.id.editTextC);
        password = findViewById(R.id.editText);
        rem = findViewById(R.id.radioButton);
        autoLogin = findViewById(R.id.radioButton6);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("rem", false)) {
            rem.setChecked(true);
            name.setText(sp.getString("name", ""));
            password.setText(sp.getString("password", ""));
            if (sp.getBoolean("auto", false)) {
                autoLogin.setChecked(true);
                signIn(sp.getString("name", ""),sp.getString("password", ""));
            }
        }
        Button but = (Button) findViewById(R.id.button2);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, zhuce.class);
                startActivity(intent);*/
                showDialog(v);
            }
        });
        Button but1 = (Button) findViewById(R.id.button);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_name = name.getText().toString();
                u_password = password.getText().toString();
                signIn(u_name,u_password);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", u_name);
                editor.putString("password", u_password);
                editor.apply();
            }
        });
        rem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(autoLogin.isChecked()){
                    rem.setChecked(true);
                    sp.edit().putBoolean("rem", true).commit();
                }else{
                    if (rem.isChecked()) {
                        sp.edit().putBoolean("rem", true).commit();
                    } else {
                        autoLogin.setChecked(false);
                        sp.edit().putBoolean("auto", false).commit();
                        sp.edit().putBoolean("rem", false).commit();
                    }
                }
            }
        });
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (autoLogin.isChecked()) {
                    rem.setChecked(true);
                    sp.edit().putBoolean("rem", true).commit();
                    sp.edit().putBoolean("auto", true).commit();
                } else {
                    sp.edit().putBoolean("auto", false).commit();
                }
            }
        });
    }
    public void showDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注册新账号");

        final View v =  getLayoutInflater().inflate(R.layout.dialog,null);
        builder.setView(v);

        builder.setPositiveButton("注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editName= v.findViewById(R.id.username);
                EditText editPwd= v.findViewById(R.id.password);
                Spinner yourShenfen=v.findViewById(R.id.yourShenfen);
                String choice = yourShenfen.getSelectedItem().toString();
                String username=editName.getText().toString();
                String password=editPwd.getText().toString();
                JSONObject info=new JSONObject();
                //int shen=1;
                try {
                    info.put("name",username);
                    info.put("pwd",password);
                    if(choice.equals("老师")){
                        //shen=2;
                        info.put("role",2);
                    }else if(choice.equals("学生")){
                        //shen=1;
                        info.put("role",1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Connect connect=new Connect();
                connect.sendReq(handler,0,"user/register","POST",info.toString(),null);
                //connect.sendReq(handler,0,"user/register?name="+username+"&pwd="+password
                        //+"&role="+shen,"GET",null,null);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}
