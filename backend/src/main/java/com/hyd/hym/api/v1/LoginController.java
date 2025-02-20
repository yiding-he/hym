package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.constants.HymError;
import com.hyd.hym.jwtsecurity.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated;

@RestController
@Slf4j
public class LoginController extends WebApiV1Controller {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public record LoginRequest(String username, String password) {
    }

    @RequestMapping("/login")
    public Response login(LoginRequest request) {

        // 构造一个 auth 传给 authenticationManager，换得一个认证后的 auth
        // 然后检查换得的 auth 状态是否是 authenticated

        var unauthenticated = unauthenticated(request.username(), request.password());
        var authenticate = authenticationManager.authenticate(unauthenticated);

        if (!authenticate.isAuthenticated()) {
            return Response.fail(HymError.UserLogin.InvalidPassword);
        }

        var token = jwtService.generate(request.username());
        return Response.success()
                .addData("token", token.token())
                .addData("createdAt", token.createdAt())
                .addData("expireAt", token.expireAt());
    }
}
