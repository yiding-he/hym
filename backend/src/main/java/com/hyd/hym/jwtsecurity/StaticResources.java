package com.hyd.hym.jwtsecurity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.stream.Stream;

public class StaticResources {

  public static final AntPathRequestMatcher[] STATIC_RESOURCE_PATTERNS = new AntPathRequestMatcher[]{
    new AntPathRequestMatcher("/**/*.html"),
    new AntPathRequestMatcher("/**/*.ico"),
    new AntPathRequestMatcher("/**/*.css"),
    new AntPathRequestMatcher("/**/*.js"),
    new AntPathRequestMatcher("/**/*.jpg"),
    new AntPathRequestMatcher("/**/*.png"),
    new AntPathRequestMatcher("/**/*.jpeg"),
    new AntPathRequestMatcher("/**/*.svg"),
    new AntPathRequestMatcher("/api/**/init-config"),  // 这个用于首页打开时获取基本信息
  };

  public static boolean isStaticResourceRequest(HttpServletRequest request) {
    return Stream.of(STATIC_RESOURCE_PATTERNS).anyMatch(m -> m.matches(request));
  }
}
