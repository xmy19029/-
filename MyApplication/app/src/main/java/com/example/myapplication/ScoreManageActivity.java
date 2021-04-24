package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.connect.Connect;


import java.util.ArrayList;
import java.util.List;

public class ScoreManageActivity extends AppCompatActivity {
    List<String> students = new ArrayList<>();
    String str = "";
    String student;
    private SharedPreferences sp;
    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return students.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            student = students.get(position);
            View view;
            if (convertView == null){
                view = View.inflate(ScoreManageActivity.this,R.layout.score_item,null);
            } else {
                view = convertView;
            }
            ImageView imageView = view.findViewById(R.id.imageView2);
            imageView.setImageResource(R.mipmap.account);
//            ImageView btn_survey = view.findViewById(R.id.button_survey);
//            btn_survey.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(ScoreManageActivity.this,fenxi.class);
//                    intent.putExtra("username",student);
//                    System.out.println(student);
//                    //startActivity(intent);
//                }
//            });
//            ImageView btn_manage = view.findViewById(R.id.button_manage);
//            btn_survey.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
//            ImageView btn_delete = view.findViewById(R.id.button_delete);
//            btn_survey.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
//            btn_survey.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            //ImageButton btn_manage = (ImageButton)findViewById(R.id.button_manage);
//            btn_manage.setImageResource(R.mipmap.record);
            //ImageButton btn_delete = (ImageButton)findViewById(R.id.button_delete);
//            btn_delete.setImageResource(R.mipmap.record);
            TextView textView = view.findViewById(R.id.text2);
            textView.setText(student);
            return view;
        }
    }
    private Handler handler2 = new Handler();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ListView listView = (ListView) findViewById(R.id.listview);
            String result = (String) msg.obj;
            result = result.replaceAll("\"","");
            result = result.replaceAll("\\[","");
            result = result.replaceAll("\\]","");
            System.out.println("replace"+result);
            String [] stringArr= result.split(",");
            for (int i = 0;i<stringArr.length;i++){
                if(stringArr[i].contains(str))
                    students.add(stringArr[i]);
            }
            final Adapter adapter = new Adapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String student = students.get(position);
                    Intent intent = new Intent(ScoreManageActivity.this,fenxi.class);
                    intent.putExtra("username",student);
                    startActivity(intent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final String studentname = students.get(position);
                    System.out.println(studentname);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScoreManageActivity.this);
                    builder.setMessage("是否删除学生"+studentname+"？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog ,int which){
                        }
                    });
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog ,int which){
                            Connect conn = new Connect();
                            String teacher;
                            conn.sendReq(handler2,1,"relations/delOne?student="+studentname+"&teacher=666777","POST",null,MainActivity.TOKEN);
                        }
                    });
                    builder.create().show();
                    return true;
                }
            });

        }
    };
    private void init(){
        Connect conn = new Connect();
        conn.sendReq(handler,1,"relations/getStudentToTeacher?teacher="+sp.getString("name",""),"POST",null,MainActivity.TOKEN);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_manage);
        str = getIntent().getStringExtra("search_info");
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        init();
        Button btn_search = (Button)findViewById(R.id.button_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreManageActivity.this, ScoreManageActivity.class);
                String search_info = ((SearchView)findViewById (R.id.searchViewCourse)).getQuery().toString() ;
                intent.putExtra("search_info",search_info);
                startActivity(intent);
            }
        });
        Button btn_new = (Button)findViewById(R.id.button_new);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScoreManageActivity.this,changemima.class);
                startActivity(intent);
            }
        });
    }
}
