package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.database.Conditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController extends WebApiV1Controller {

  @GetMapping("/list")
  public Response listUsers(@RequestBody Conditions conditions) {
    log.info("list users: {}", conditions);
    return Response.ok();
  }

}
