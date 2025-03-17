package com.hyd.hym.jwtsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated;

@Service
public class JwtLoginService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  /**
   * 实现登录
   *
   * @param username 用户名
   * @param password 密码
   * @return 如果登录失败，返回 null；否则返回 JWT 令牌
   */
  public JwtToken login(String username, String password) {
    var unauthenticated = unauthenticated(username, password);
    try {
      var authenticate = authenticationManager.authenticate(unauthenticated);
      return authenticate.isAuthenticated() ? jwtService.generate(username) : null;
    } catch (AuthenticationException e) {
      return null;
    }
  }
}
