package com.hyd.hym.api.v1;

import com.hyd.hym.HymApplicationTest;
import com.hyd.hym.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends HymApplicationTest {

  @Test
  void listUsers() {
    loginAsAdmin();
    var response = callApiGet("/user/list", Map.of(
      "user_name$eq", "admin"
    ));
    System.out.println(response);
  }
}