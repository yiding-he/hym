package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.events.UserEvents;
import com.hyd.hym.jwtsecurity.JwtLoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController extends WebApiV1Controller {

  @Autowired
  private JwtLoginService jwtLoginService;

  public record LoginRequest(String username, String password) {
  }

  @PostMapping("/login")
  public Response login(@RequestBody LoginRequest request, HttpServletRequest req) {
    return jwtLoginService
      .login(request.username(), request.password())
      .ifOk(
        jwt -> {
          publisher.publishEvent(new UserEvents.UserLoggedIn(
            request.username(), req.getRemoteAddr()
          ));
          return Response.ok()
            .addData("token", jwt.token())
            .addData("createdAt", jwt.createdAt())
            .addData("expireAt", jwt.expireAt());
        }
      ).orElse(Response::error);

  }
}
