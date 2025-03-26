package com.hyd.hym.jwtsecurity;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hyd.hym.HymConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.util.concurrent.TimeUnit.*;

/**
 * 对 JWT 相关功能的封装，一些功能会根据配置文件来配置。
 */
@Service
public class JwtService {

  @Autowired
  private HymConfig hymConfig;

  private SecretKey secretKey;

  private JwtParser jwtParser;

  private Cache<String, Claims> claimsCache;

  @PostConstruct
  private void init() {
    // 加载相关配置，初始化 JwtParser 以及缓存
    this.secretKey = Keys.hmacShaKeyFor(hymConfig.getJwt().getSecret().getBytes());
    this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    this.claimsCache = Caffeine.newBuilder()
      .maximumSize(hymConfig.getJwt().getCacheSize())
      .expireAfterWrite(10, MINUTES)
      .build();
  }

  public Claims parse(String jwt) {
    return this.claimsCache.get(jwt, __ ->
      jwtParser.parseSignedClaims(__).getPayload()
    );
  }

  public JwtToken generate(String username) {
    var now = System.currentTimeMillis();
    var exp = now + hymConfig.getJwt().getTtlSeconds() * 1000L;
    var token = Jwts.builder()
      .subject(username)
      .issuedAt(new Date(now))
      .expiration(new Date(exp))
      .signWith(secretKey)
      .compact();

    return new JwtToken(token, String.valueOf(now), String.valueOf(exp));
  }
}
