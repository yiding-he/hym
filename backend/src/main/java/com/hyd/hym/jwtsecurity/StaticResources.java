package com.hyd.hym.jwtsecurity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;

import java.util.stream.Stream;

public class StaticResources {

  private static final AntPathMatcher pathMatcher = new AntPathMatcher();

  public static final String[] STATIC_RESOURCE_PATTERNS = new String[]{
    "/**/*.html",
    "/**/*.ico",
    "/**/*.css",
    "/**/*.js",
    "/**/*.jpg",
    "/**/*.png",
    "/**/*.jpeg",
    "/**/*.svg",
    "/api/**/init-config",   // 这个用于首页打开时获取基本信息
    "/api/**/login",         // 这个用于登录
  };

  public static boolean isStaticResourceRequest(HttpServletRequest request) {
    return isStaticResourceRequest(request.getRequestURI());
  }

  public static boolean isStaticResourceRequest(String uri) {
    return Stream.of(STATIC_RESOURCE_PATTERNS).anyMatch(m -> pathMatcher.match(m, uri));
  }
}
