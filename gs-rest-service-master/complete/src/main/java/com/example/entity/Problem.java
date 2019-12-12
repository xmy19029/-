package com.example.entity;

public class Problem {
  private int tid;
  private String textMain;
  private String textA;
  private String textB;
  private String textC;
  private String textD;
  private boolean isA;
  private boolean isB;
  private boolean isC;
  private boolean isD;

  public Problem(){}
  public Problem(String textMain, String textA, String textB, String textC, String textD, boolean isA, boolean isB, boolean isC, boolean isD) {
    this.textMain = textMain;
    this.textA = textA;
    this.textB = textB;
    this.textC = textC;
    this.textD = textD;
    this.isA = isA;
    this.isB = isB;
    this.isC = isC;
    this.isD = isD;
  }

  public int getTid() {
    return tid;
  }

  public void setTid(int tid) {
    this.tid = tid;
  }

  public String getTextMain() {
    return textMain;
  }

  public void setTextMain(String textMain) {
    this.textMain = textMain;
  }

  public String getTextA() {
    return textA;
  }

  public void setTextA(String textA) {
    this.textA = textA;
  }

  public String getTextB() {
    return textB;
  }

  public void setTextB(String textB) {
    this.textB = textB;
  }

  public String getTextC() {
    return textC;
  }

  public void setTextC(String textC) {
    this.textC = textC;
  }

  public String getTextD() {
    return textD;
  }

  public void setTextD(String textD) {
    this.textD = textD;
  }

  public boolean isA() {
    return isA;
  }

  public void setIsA(boolean a) {
    isA = a;
  }

  public boolean isB() {
    return isB;
  }

  public void setIsB(boolean b) {
    isB = b;
  }

  public boolean isC() {
    return isC;
  }

  public void setIsC(boolean c) {
    isC = c;
  }

  public boolean isD() {
    return isD;
  }

  public void setIsD(boolean d) {
    isD = d;
  }
}
