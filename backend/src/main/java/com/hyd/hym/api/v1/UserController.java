package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.database.Condition;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.ConditionsHttpParser;
import com.hyd.hym.database.Operator;
import com.hyd.hym.mappers.HymUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class UserController extends WebApiV1Controller {

  @Autowired
  private HymUserMapper hymUserMapper;

  @GetMapping("/user/list")
  public Response listUsers(@ModelAttribute Conditions conditions) {
    log.info("list users: {}", conditions.getConditions());
    return Response.ok()
      .addData("users", hymUserMapper.listUser(conditions));
  }

}
