package com.hyd.hym.api.v1;

import com.hyd.hym.HymApplicationTest;
import com.hyd.hym.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest extends HymApplicationTest {

  @Test
  void login() {
    var request = new LoginController.LoginRequest("admin", "admin123");
    var response = restTemplate.postForEntity("http://localhost:8080/api/v1/login", request, Response.class);
    System.out.println(response.getBody());
  }
}