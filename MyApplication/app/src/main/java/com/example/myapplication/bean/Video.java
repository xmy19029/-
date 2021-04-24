package com.example.myapplication.bean;

import java.io.Serializable;

public class Video implements Serializable {
    private int idvideo;
    private int idcourse;
    private String title;
    private String file;

    public Video(int idvideo, int idcourse, String title, String file) {
        this.idvideo = idvideo;
        this.idcourse = idcourse;
        this.title = title;
        this.file = file;
    }
    public Video(){}

    public int getIdvideo() {
        return idvideo;
    }

    public void setIdvideo(int idvideo) {
        this.idvideo = idvideo;
    }

    public int getIdcourse() {
        return idcourse;
    }

    public void setIdcourse(int idcourse) {
        this.idcourse = idcourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Video{" +
                "idvideo=" + idvideo +
                ", idcourse=" + idcourse +
                ", title='" + title + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
