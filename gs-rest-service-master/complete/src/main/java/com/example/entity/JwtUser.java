package com.example.entity;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

public class JwtUser implements UserDetails {
  @Override
  public String toString() {
    return "JwtUser{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", authorities=" + authorities +
            '}';
  }

  private int id;
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  public JwtUser(){
  }
  public JwtUser(User user){
    id = user.getId();
    username = user.getUsername();
    password = user.getPassword();
    authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));

  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
