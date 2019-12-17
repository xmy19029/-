package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @RequestMapping(value = "getUserById",method = RequestMethod.GET)
  public String getUser( int id){
    User t = userService.getUser(id);
    if(t==null) return "No such man";
    else return t.toString();
  }

  @RequestMapping(value = "register",method = RequestMethod.POST)
  public String addUser(@RequestParam("name") String userName,@RequestParam("pwd") String password,@RequestParam("role") String role){
    User t = new User();
    t.setUsername(userName);
    t.setPassword(bCryptPasswordEncoder.encode(password));
    int idrole = Integer.parseInt(role);
    if(idrole==1) t.setRole("ROLE_STUDENT");
    else t.setRole("ROLE_TEACHER");
    return ""+userService.addUser(t);
  }

}
