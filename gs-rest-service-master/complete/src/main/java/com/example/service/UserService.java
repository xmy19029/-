package com.example.service;

import com.example.entity.*;
import com.example.entity.User;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class UserService implements UserDetailsService{
  @Autowired
  UserMapper userMapper;
  public User getUser(int id){
    return userMapper.getUser(id);
  }
  public int addUser(User user){
    return userMapper.addUser(user);
  }
  public int delUser(int id){
    return userMapper.delUser(id);
  }
  public int updateUser(User user){
    return userMapper.updateUser(user);
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userMapper.getUserByName(s);
    return new JwtUser(user);
  }
}
