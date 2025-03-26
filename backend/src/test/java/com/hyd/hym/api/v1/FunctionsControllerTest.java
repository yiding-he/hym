package com.hyd.hym.api.v1;

import com.hyd.hym.HymApplicationTest;
import com.hyd.hym.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsControllerTest extends HymApplicationTest {

  @Test
  void getFunctions() {
    loginAs("admin", "admin123");
    var url = "http://localhost:8080/api/v1/functions";
    var response = restTemplate.getForEntity(url, Response.class);
    assertEquals(200, response.getStatusCode().value());
    assertTrue(response.getBody().isOk());
    System.out.println(response.getBody());
  }
}