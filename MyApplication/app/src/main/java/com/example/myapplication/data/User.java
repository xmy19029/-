package com.example.myapplication.data;

public class User {
    private int id;
    private String name;
    private String school;
    private String college;//学院
    private String major;
    private String password;
    private String identity;
    public User(int id,String name,String school,String college,
                     String major,String password,String identity){
        this.id=id;
        this.name=name;
        this.school=school;
        this.college=college;
        this.major=major;
        this.password=password;
        this.identity=identity;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public String getCollege() {
        return college;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSchool() {
        return school;
    }

    public String getIdentity() {
        return identity;
    }
}
