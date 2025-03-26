package com.hyd.hym.jwtsecurity;

import com.hyd.hym.Result;
import com.hyd.hym.constants.HymError.UserLogin;
import com.hyd.hym.mappers.HymUserMapper;
import com.hyd.hym.models.HymUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.hyd.hym.Result.error;
import static com.hyd.hym.Result.ok;

@Service
public class JwtLoginService {

  @Autowired
  private HymUserMapper hymUserMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  /**
   * 实现登录
   *
   * @param username 用户名
   * @param password 密码
   * @return 如果登录失败，返回 null；否则返回 JWT 令牌
   */
  public Result<JwtToken> login(String username, String password) {
    var hymUser = hymUserMapper.selectForUserLogin(username);
    if (hymUser == null) {
      return error(UserLogin.UserNotFound);

    } else if (hymUser.getStatus() == null || hymUser.getStatus() != HymUser.STATUS_NORMAL) {
      return error(UserLogin.UserDisabled);

    } else if (!passwordEncoder.matches(password, hymUser.getPassword())) {
      return error(UserLogin.InvalidPassword);
    }
    return ok(jwtService.generate(username));
  }
}
