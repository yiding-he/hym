package com.hyd.hym.jwtsecurity;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hyd.hym.HymConfig;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.util.concurrent.TimeUnit.*;

/**
 * 对 JWT 相关功能的封装，一些功能会根据配置文件来配置。
 */
@Service
@Slf4j
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

  /**
   * 解析 JWT 字符串，返回 Claims 对象。如果解析失败，则返回 null，此时需要用户重新登录
   */
  public Claims parse(String jwt) {
    try {
      return this.claimsCache.get(jwt, __ ->
        jwtParser.parseSignedClaims(__).getPayload()
      );
    } catch (ClaimJwtException e) {
      log.error("JWT 解析失败: {}", e.getMessage());
      return null;
    }
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
