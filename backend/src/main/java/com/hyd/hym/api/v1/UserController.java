package com.hyd.hym.api.v1;

import com.hyd.hybatis.Conditions;
import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.database.*;
import com.hyd.hym.mappers.HymUserMapper;
import com.hyd.hym.models.HymUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class UserController extends WebApiV1Controller {

  @Autowired
  private HymUserMapper hymUserMapper;

  @Autowired
  private IdSupplier idSupplier;

  @GetMapping("/user/list")
  public Response listUsers(Conditions conditions, HttpServletRequest request) throws Exception {
    Thread.sleep(1000);
    var page = hymUserMapper.selectPage(conditions, request);
    return Response.ok().addPage(page);
  }

  public record AddUserParams(String userName, String mobile, String email) {

  }

  @PostMapping("/user/add")
  public Response addUser(@RequestBody AddUserParams params) {
    var user = new HymUser();
    user.setHymUserId(idSupplier.nextId());
    user.setUserName(params.userName);
    user.setMobile(params.mobile);
    user.setEmail(params.email);
    hymUserMapper.insert(user);
    return Response.ok();
  }
}
