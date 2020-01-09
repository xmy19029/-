package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.connect.Connect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class examing extends Activity {
    private ArrayList<Integer> res=new ArrayList<>();//保存答案
    private ArrayList<Integer> problems=new ArrayList<>();//试题编号
    private HashMap<Integer,Integer> isDone=new HashMap<>();
    private int index=-1;//当前试题，从0开始
    private int num=10;//试题数量
    private int sum=0;//已做数目
    private int examId=0;//试卷编号
    private SharedPreferences sp;
    private TextView problem,now,all;
    private CheckBox a,b,c,d;
    private Button back,fore,check,go;
    private ProgressBar progressBar;
    private JSONObject getProblem;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.arg1==0){
                examId=Integer.valueOf(msg.obj.toString());
                Connect con=new Connect();
                con.sendReq(handler,2,"exams/getOne?eid="+examId,"GET",null,MainActivity.TOKEN);
            }else if(msg.arg1==1){
                try {
                    getProblem=new JSONObject(msg.obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                upUI();
            }else if(msg.arg1==2){
                try {
                    problems.clear();
                    JSONObject exam=new JSONObject(msg.obj.toString());
                    String[] tihao=exam.getString("problemList").split(",");
                    for(String s:tihao){
                        problems.add(Integer.valueOf(s));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(msg.obj);
        }
    };
    private void submit(){
        JSONObject result=new JSONObject();
        try {
            result.put("username",sp.getString("name",""));
            result.put("eid",examId);
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<res.size();i++){
                if(i==0) sb.append(res.get(i));
                else sb.append(","+res.get(i));
            }
            result.put("chosen",sb.toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(result.toString());
        Connect con=new Connect();
        con.sendReq(handler,3,"submit/addOne","POST",result.toString(),MainActivity.TOKEN);
    }
    private void upUI(){
        onCreate(null);
        progressBar.setProgress(sum*9);
        now.setText("第"+(index+1)+"题");
        all.setText("共"+num+"题");
        if(res.get(index*4)==1) a.setChecked(true);
        else a.setChecked(false);
        if(res.get(index*4+1)==1) b.setChecked(true);
        else b.setChecked(false);
        if(res.get(index*4+2)==1) c.setChecked(true);
        else c.setChecked(false);
        if(res.get(index*4+3)==1) d.setChecked(true);
        else d.setChecked(false);
        try {
            problem.setText(getProblem.getString("textMain"));
            a.setText(getProblem.getString("textA"));
            b.setText(getProblem.getString("textB"));
            c.setText(getProblem.getString("textC"));
            d.setText(getProblem.getString("textD"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getProblem=null;
    }
    private void upData(){
        //保存此题答案
        Boolean flag=false;
        if(a.isChecked()){
            res.set(index*4,1);
            flag=true;
            isDone.put(index,1);
        }else{
            res.set(index*4,0);
        }
        if(b.isChecked()){
            res.set(index*4+1,1);
            flag=true;
            isDone.put(index,1);
        }else{
            res.set(index*4+1,0);
        }
        if(c.isChecked()){
            res.set(index*4+2,1);
            flag=true;
            isDone.put(index,1);
        }else{
            res.set(index*4+2,0);
        }
        if(d.isChecked()){
            res.set(index*4+3,1);
            flag=true;
            isDone.put(index,1);
        }else{
            res.set(index*4+3,0);
        }
        if(flag==false) isDone.put(index,0);
        sum=0;
        for(int i=0;i<num;i++){
            if(isDone.get(i)==1){
                sum++;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examing);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //根据获得的试题数量，初始化答案列表全为0,是否作答全为0
        if(index==-1){
            //获取试题编号
            Connect con=new Connect();
            con.sendReq(handler,0,"exams/getRandomOne?num=10","GET",null,MainActivity.TOKEN);
            for(int i=0;i<num;i++){
                res.add(0);
                res.add(0);
                res.add(0);
                res.add(0);
                isDone.put(i,0);
            }
        }
        problem=findViewById(R.id.textView32);
        now=findViewById(R.id.textView61);
        all=findViewById(R.id.textView63);
        a=findViewById(R.id.radioButton2);
        b=findViewById(R.id.radioButton3);
        c=findViewById(R.id.radioButton4);
        d=findViewById(R.id.radioButton5);
        back=findViewById(R.id.button14);
        fore=findViewById(R.id.button13);
        check=findViewById(R.id.button15);
        progressBar=findViewById(R.id.progressBar);
        go=findViewById(R.id.button26);
        progressBar.setMax(num*9);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==-1){
                    index=0;
                    //更新第一题
                    Connect con=new Connect();
                    con.sendReq(handler,1,"problems/getOne?id="+problems.get(index),"GET",null,MainActivity.TOKEN);
                }else{
                    Toast.makeText(examing.this,"考试已经开始！",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==-1){
                    Toast.makeText(examing.this,"请点击开始按钮开始考试",
                            Toast.LENGTH_LONG).show();
                }else {
                    upData();
                    Connect con=new Connect();
                    con.sendReq(handler,1,"problems/getOne?id="+problems.get(index),"GET",null,MainActivity.TOKEN);
                    if(sum<num){//没做完
                        StringBuilder stringBuilder=new StringBuilder();
                        for(int i=0;i<num;i++){
                            if(isDone.get(i)==0){
                                stringBuilder.append((i+1)+",");
                            }
                        }
                        new AlertDialog.Builder(examing.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("注意")
                                .setMessage("第"+stringBuilder.toString().trim()+"题还没做！")
                                .setPositiveButton("交卷", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //提交并关闭
                                        submit();
                                        finish();//Exit Activity
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                }).create().show();
                    }else{
                        //提交并关闭
                        submit();
                        finish();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==-1){
                    Toast.makeText(examing.this,"请点击开始按钮开始考试",
                            Toast.LENGTH_LONG).show();
                }else{
                    upData();
                    if(index==(num-1)){//最后一题了
                        Toast.makeText(examing.this,"这是最后一题",
                                Toast.LENGTH_LONG).show();
                    }else {
                        //更新试题
                        index++;
                    }
                    Connect con=new Connect();
                    con.sendReq(handler,1,"problems/getOne?id="+problems.get(index),"GET",null,MainActivity.TOKEN);
                }
            }
        });
        fore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==-1){
                    Toast.makeText(examing.this,"请点击开始按钮开始考试",
                            Toast.LENGTH_LONG).show();
                }else{
                    upData();
                    if(index==0){
                        Toast.makeText(examing.this,"这是第一题",
                                Toast.LENGTH_LONG).show();
                    }else{
                        //更新试题
                        index--;
                    }
                    Connect con=new Connect();
                    con.sendReq(handler,1,"problems/getOne?id="+problems.get(index),"GET",null,MainActivity.TOKEN);
                }
            }
        });
    }
}
