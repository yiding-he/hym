package com.hyd.hym;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyd.hym.api.v1.LoginController;
import com.hyd.hym.constants.HymError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
  properties = {
    "debug=true"
  })
@ActiveProfiles({"dev", "unit"})
public class HymApplicationTest {

  protected RestTemplate restTemplate = new RestTemplate();
  protected ObjectMapper objectMapper = new ObjectMapper();

  protected void loginAs(String username, String password) {
    var token = callApiPost(
      "/login",
      new LoginController.LoginRequest(username, password)
    ).getData("token", String.class);

    if (StringUtils.hasText(token)) {
      restTemplate.getInterceptors().add((request, body, execution) -> {
        request.getHeaders().add("Authorization", "Bearer " + token);
        return execution.execute(request, body);
      });
    }
  }

  protected void loginAsAdmin() {
    loginAs("admin", "admin123");
  }

  protected static String api(String path) {
    return "http://localhost:8080/api/v1" + path;
  }

  protected Response callApi(HttpMethod method, String path, Object body) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<String> requestEntity;
      if (body != null) {
        String bodyJson = objectMapper.writeValueAsString(body);
        requestEntity = new HttpEntity<>(bodyJson, headers);
      } else {
        requestEntity = new HttpEntity<>(headers);
      }

      var resp = restTemplate.exchange(api(path), method, requestEntity, Response.class);
      return resp.getBody();
    } catch (Exception e) {
      throw HymError.Core.NetworkError.toException(e);
    }
  }

  protected Response callApiGet(String path, Map<String, Object> params) {
    var builder = UriComponentsBuilder.fromPath(path);
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      builder.queryParam(entry.getKey(), entry.getValue());
    }
    return callApi(HttpMethod.GET, builder.toUriString(), null);
  }

  protected Response callApiPost(String path, Object body) {
    return callApi(HttpMethod.POST, path, body);
  }

  @Test
  public void contextLoads() {
    assertTrue(true);
  }
}