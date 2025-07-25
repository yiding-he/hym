package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.database.*;
import com.hyd.hym.mappers.HymUserMapper;
import com.hyd.hym.models.HymUser;
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
  public Response listUsers(@ModelAttribute Conditions conditions) throws Exception {
    Thread.sleep(1000);
    var page = hymUserMapper.listPage(conditions);
    return Response.ok()
      .addData("rows", page.data())
      .addData("total", page.totalCount())
      .addData("pageSize", page.pageSize())
      .addData("pageIndex", page.pageIndex())
      .addData("totalPage", page.totalPage());
  }

}
