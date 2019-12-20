package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase database){
        String sql="create table user(id integer primary key,name varchar,school varchar,college varchar," +
                "major varchar,password varchar,identity varchar)";
        database.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){

    }
    public static synchronized void addUser(User user,SQLiteDatabase database){
        System.out.println("添加");
        try{
            ContentValues cv=new ContentValues();
            cv.put("id",user.getId());
            cv.put("name",user.getName());
            cv.put("school",user.getSchool());
            cv.put("college",user.getCollege());
            cv.put("major",user.getMajor());
            cv.put("password",user.getPassword());
            cv.put("identity",user.getIdentity());
            database.insert("user",null,cv);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
    }
    public static synchronized ArrayList<User> find(SQLiteDatabase database){
        Cursor cursor=null;
        ArrayList<User> res=new ArrayList<>();
        String sql="select * from "+"user";
        try{
            cursor=database.rawQuery(sql,null);
            while(cursor.moveToNext()){
                int id=cursor.getInt((cursor.getColumnIndex("id")));
                String name=cursor.getString((cursor.getColumnIndex("name")));
                String school=cursor.getString((cursor.getColumnIndex("school")));
                String college=cursor.getString((cursor.getColumnIndex("college")));
                String major=cursor.getString((cursor.getColumnIndex("major")));
                String password=cursor.getString((cursor.getColumnIndex("password")));
                String identity=cursor.getString((cursor.getColumnIndex("identity")));
                User user=new User(id,name,school,college,major,password,identity);
                res.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if(database!=null){
                database.close();
            }
        }
        return res;
    }
}
