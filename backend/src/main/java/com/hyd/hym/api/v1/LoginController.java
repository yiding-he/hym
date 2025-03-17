package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.constants.HymError;
import com.hyd.hym.jwtsecurity.JwtLoginService;
import com.hyd.hym.mappers.HymUserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class LoginController extends WebApiV1Controller {

  @Autowired
  private JwtLoginService jwtLoginService;

  @Autowired
  private HymUserMapper hymUserMapper;

  public record LoginRequest(String username, String password) {
  }

  @PostMapping("/login")
  public Response login(@RequestBody LoginRequest request, HttpServletRequest req) {
    var token = jwtLoginService.login(request.username(), request.password());
    if (token == null) {
      return Response.fail(HymError.UserLogin.InvalidPassword);
    } else {
      var ip = Optional.ofNullable(req.getRemoteAddr()).orElse("0.0.0.0");
      hymUserMapper.updateLastLoginTime(request.username(), ip);
      return Response.success()
        .addData("token", token.token())
        .addData("createdAt", token.createdAt())
        .addData("expireAt", token.expireAt());
    }
  }
}
