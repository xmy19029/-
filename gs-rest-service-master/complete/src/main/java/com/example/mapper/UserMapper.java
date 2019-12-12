package com.example.mapper;

import com.example.entity.*;
import org.springframework.stereotype.*;

@Repository
public interface UserMapper {
  User getUser(int id);
  User getUserByName(String name);
  int addUser(User user);
  int delUser(int id);
  int updateUser(User user);
  int getIdByUsername(String name);
}
