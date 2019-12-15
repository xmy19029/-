package com.example.mapper;

import com.example.entity.*;

import java.util.*;

public interface PostMapper {
  public int addPost(Post post);
  public int delPost(int id);
  public List<Post> getAllPosts();
  public List<Post> getMyPosts(String username);
  public Post getOnePost(int id);
  public int updateText(int id,String text);
}
