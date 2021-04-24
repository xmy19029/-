package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import com.example.myapplication.bean.ClassCourse;
import com.example.myapplication.bean.Posts;
import com.example.myapplication.connect.Connect;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class course extends AppCompatActivity {
    List<ClassCourse> courses = new ArrayList<>();
    String str = "";
    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return courses.size();
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
            ClassCourse classCourse = courses.get(position);
            View view;
            if (convertView == null){
                view = View.inflate(course.this,R.layout.forum_item,null);
            } else {
                view = convertView;
            }
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(getResource(classCourse.getPic()));
            TextView textView = view.findViewById(R.id.text);
            textView.setText(classCourse.getTitle());
            TextView teacher = view.findViewById(R.id.textViewTeacher);
            teacher.setText(classCourse.getTname());
            return view;
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ListView listView = (ListView) findViewById(R.id.listview);
            String result = (String) msg.obj;
            System.out.println(result);
            JSONArray jsonArray = JSONArray.fromObject(result);
            for (int i = 0;i<jsonArray.size();i++) {
                JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i).toString());
                ClassCourse classCourse = new ClassCourse();
                classCourse.setIdcourse((Integer)jsonObject.get("idcourse"));
                classCourse.setPic((String)jsonObject.get("pic"));
                classCourse.setTitle((String)jsonObject.get("title"));
                classCourse.setTname((String)jsonObject.get("tname"));
                classCourse.setTid((Integer)jsonObject.get("tid"));
                System.out.println(classCourse.getTitle());
                if(classCourse.getTitle().contains(str))
                    courses.add(classCourse);
            }
            final Adapter adapter = new Adapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ClassCourse classCourse = courses.get(position);
                    Intent intent = new Intent(course.this,courseDetail.class);
                    intent.putExtra("idcourse",classCourse.getIdcourse());
                    intent.putExtra("courseTitle",classCourse.getTitle());
                    intent.putExtra("teacherName",classCourse.getTname());
                    intent.putExtra("pic",classCourse.getPic());
                    startActivity(intent);
                }
            });
        }
    };
    public int  getResource(String imageName){
        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
    private void init(){
        Connect conn = new Connect();
        conn.sendReq2(handler,1,"course/getAll","GET",null,MainActivity.TOKEN);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        if(getIntent().getStringExtra("search_info") != null)
            str = getIntent().getStringExtra("search_info");
        else
            str = "";
        init();
        Button btn_search = (Button)findViewById(R.id.button_search2);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(course.this,course.class);
                String search_info = ((SearchView)findViewById (R.id.searchViewCourse)).getQuery().toString() ;
                intent.putExtra("search_info",search_info);
                startActivity(intent);
            }
        });
        Button button = (Button)findViewById(R.id.button_new2);
        if(!getIntent().getBooleanExtra("teacher",false)){
            button.setEnabled(false);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(course.this,AddCourse.class);
                startActivity(intent);
            }
        });
    }
}
