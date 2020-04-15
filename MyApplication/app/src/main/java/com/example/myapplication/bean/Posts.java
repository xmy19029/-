package com.example.myapplication.bean;

import java.io.Serializable;

public class Posts implements Serializable {
    String title;
    Integer pid;
    String poster;
    String text;
    Integer lastReplyNum;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLastReplyNum() {
        return lastReplyNum;
    }

    public void setLastReplyNum(Integer lastReplyNum) {
        this.lastReplyNum = lastReplyNum;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "title='" + title + '\'' +
                ", pid=" + pid +
                ", poster='" + poster + '\'' +
                ", text='" + text + '\'' +
                ", lastReplyNum=" + lastReplyNum +
                '}';
    }
}
