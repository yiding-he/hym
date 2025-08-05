package com.hyd.hym.api.v1;

import com.hyd.hybatis.Conditions;
import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.mappers.HymRoleMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RoleController extends WebApiV1Controller {

  @Autowired
  private HymRoleMapper hymRoleMapper;

  @GetMapping("/role/list")
  public Response listRoles(Conditions conditions, HttpServletRequest request) throws Exception {
    Thread.sleep(1000);
    var page = hymRoleMapper.selectPage(conditions, request);
    return Response.ok().addPage(page);
  }

}