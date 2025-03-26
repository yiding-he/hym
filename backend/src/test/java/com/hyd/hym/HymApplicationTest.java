package com.hyd.hym;

import com.hyd.hym.api.v1.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"dev", "unit"})
public class HymApplicationTest {

  protected RestTemplate restTemplate = new RestTemplate();

  protected void loginAs(String username, String password) {
    var token = restTemplate.postForEntity(
      "http://localhost:8080/api/v1/login",
      new LoginController.LoginRequest(username, password),
      Response.class
    ).getBody().getData("token", String.class);

    if (StringUtils.hasText(token)) {
      restTemplate.getInterceptors().add((request, body, execution) -> {
        request.getHeaders().add("Authorization", "Bearer " + token);
        return execution.execute(request, body);
      });
    }
  }

  @Test
  public void contextLoads() {
    assertTrue(true);
  }
}