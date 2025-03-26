package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController extends WebApiV1Controller {

    @RequestMapping("/init-config")
    public Response initConfig() {
        return Response.ok().addData("applicationName", "某某系统");
    }
}
