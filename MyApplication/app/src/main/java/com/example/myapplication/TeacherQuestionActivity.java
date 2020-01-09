package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.connect.Connect;

import org.json.JSONException;
import org.json.JSONObject;

public class TeacherQuestionActivity extends AppCompatActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(TeacherQuestionActivity.this,TeacherQuestionActivity.class);
            startActivity(intent);
            Toast.makeText(TeacherQuestionActivity.this,"试题添加成功！",Toast.LENGTH_SHORT).show();
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_question);
        Button btn = (Button)findViewById(R.id.save_button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String textMain =((EditText)findViewById (R.id.add_content)).getText().toString();
                String textA = ((EditText)findViewById (R.id.editTextA)).getText().toString() ;
                String textB = ((EditText)findViewById (R.id.editTextB)).getText().toString() ;
                String textC = ((EditText)findViewById (R.id.editTextC)).getText().toString() ;
                String textD = ((EditText)findViewById (R.id.editTextD)).getText().toString() ;
                CheckBox A = (CheckBox)findViewById(R.id.checkBoxA);
                CheckBox B = (CheckBox)findViewById(R.id.checkBoxB);
                CheckBox C = (CheckBox)findViewById(R.id.checkBoxC);
                CheckBox D = (CheckBox)findViewById(R.id.checkBoxD);
                try {
                    JSONObject object= new JSONObject();
                    object.put("textMain",textMain);
                    object.put("textA",textA);
                    object.put("textB",textB);
                    object.put("textC",textC);
                    object.put("textD",textD);
                    object.put("a",A.isChecked());
                    object.put("b",B.isChecked());
                    object.put("c",C.isChecked());
                    object.put("d",D.isChecked());
                    Connect conn = new Connect();
                    String result = null;
                    result = conn.sendReq(handler,1,"problems/addOne ","POST",String.valueOf(object),MainActivity.TOKEN);
                    System.out.println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(textA);
            }
        });
    }
}
