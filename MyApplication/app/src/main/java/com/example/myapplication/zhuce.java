package com.example.myapplication;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.data.DBHelper;
import com.example.myapplication.data.User;

import java.util.ArrayList;

public class zhuce extends Activity {
    private int id;
    private String name,school,college,major,password,identity;
    private SQLiteDatabase database;
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);
        helper=new DBHelper(this,"myDb.db",null,1);
        database=helper.getWritableDatabase();
        Button button=findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Spinner spinner2=(Spinner) findViewById(R.id.spinner2);
                school=spinner2.getSelectedItem().toString();
                Spinner spinner4=(Spinner) findViewById(R.id.spinner4);
                college=spinner4.getSelectedItem().toString();
                Spinner spinner3=(Spinner) findViewById(R.id.spinner3);
                identity=spinner3.getSelectedItem().toString();
                EditText id_e=findViewById(R.id.editText3);
                EditText name_e=findViewById(R.id.editText4);
                EditText major_e=findViewById(R.id.editText5);
                EditText password_e=findViewById(R.id.editText6);
                id=Integer.parseInt(id_e.getEditableText().toString().trim());
                name=name_e.getText().toString();
                major=major_e.getText().toString();
                password=password_e.getText().toString();
                User user=new User(id,name,school,college,major,password,identity);
                DBHelper.addUser(user,database);
            }
        });
    }
}
