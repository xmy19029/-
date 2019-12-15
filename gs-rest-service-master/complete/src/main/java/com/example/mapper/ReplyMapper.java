package com.example.mapper;

import com.example.entity.*;

import java.util.*;

public interface ReplyMapper {
  public int addReply(Reply reply);
  public int delReply(int id);
  public Reply getOneReply(int id);
  public List<Reply> getReplysOfPost(int pid);
  public int updateText(int id,String text);
}
