package com.hyd.hym;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "hym")
@Data
public class HymConfig {

  @Data
  public static class Jwt {
    private String secret = "hym-jwt-s3cret|hym-jwt-s3cret|hym-jwt-s3cret|hym-jwt-s3cret|hym-jwt-s3cret|hym-jwt-s3cret|hym-jwt-s3cret";
    private int ttlSeconds = 360000;
    private int cacheSize = 1000;
    private String authHeader = "Authorization";
    private String authType = "Bearer";
  }

  ////////////////////////////

  @NestedConfigurationProperty
  private Jwt jwt = new Jwt();
}
