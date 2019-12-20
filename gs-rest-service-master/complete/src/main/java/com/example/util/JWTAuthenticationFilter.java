package com.example.util;

import com.example.entity.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    super.setFilterProcessesUrl("/api/v1/user/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {

    // 从输入流中获取到登录的信息
    try {
      LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
      return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
      );
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  // 成功验证后调用的方法
  // 如果验证成功，就生成token并返回
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {

    // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
    // 所以就是JwtUser啦
    JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
    System.out.println("jwtUser:" + jwtUser.toString());
    String token = JwtTokenUtils.createToken(jwtUser.getUsername(), false);
    // 返回创建成功的token
    // 但是这里创建的token只是单纯的token
    // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
    response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
  }

  // 这是验证失败时候调用的方法
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    //response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    response.getWriter().write("authentication failed");
  }
}
