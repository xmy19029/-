package com.example.entity;

public class Student {
  private Integer sid;
  private String name;
  private String acadmy;
  private String major;
  private String grade;

  public Integer getSid() {
    return sid;
  }

  public void setSid(Integer sid) {
    this.sid = sid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAcadmy() {
    return acadmy;
  }

  public void setAcadmy(String acadmy) {
    this.acadmy = acadmy;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  @Override
  public String toString() {
    return "Student{" +
            "sid=" + sid +
            ", name='" + name + '\'' +
            ", acadmy='" + acadmy + '\'' +
            ", major='" + major + '\'' +
            ", grade='" + grade + '\'' +
            '}';
  }
}
