package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.mappers.HymRoleMapper;
import com.hyd.hym.models.HymRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RoleController extends WebApiV1Controller {

  @Autowired
  private HymRoleMapper hymRoleMapper;

  @GetMapping("/role/list")
  public Response listRoles(@ModelAttribute Conditions conditions) throws Exception {
    Thread.sleep(1000);
    var page = hymRoleMapper.listPage(conditions);
    return Response.ok()
      .addData("rows", page.data())
      .addData("total", page.totalCount())
      .addData("pageSize", page.pageSize())
      .addData("pageIndex", page.pageIndex())
      .addData("totalPage", page.totalPage());
  }

}