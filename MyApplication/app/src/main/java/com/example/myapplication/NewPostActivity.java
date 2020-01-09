package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.bean.Posts;
import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPostActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(NewPostActivity.this,"发布成功",Toast.LENGTH_LONG).show();
            finish();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Button btn = (Button)findViewById(R.id.button_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textA = ((EditText)findViewById (R.id.add_content2)).getText().toString() ;
                String textB = ((EditText)findViewById (R.id.editTitle)).getText().toString() ;
                try {
                    JSONObject object= new JSONObject();
                    object.put("poster","test");
                    object.put("text",textA);
                    object.put("title",textB);
                    Connect conn = new Connect();
                    String result = null;
                    result = conn.sendReq(handler,1,"posts/addOne ","POST",String.valueOf(object),MainActivity.TOKEN);
                    System.out.println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
