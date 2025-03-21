package com.hyd.hym.jwtsecurity;

import com.hyd.hym.HymConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 基于 JWT 的 Spring Security 过滤器。当请求头中包含正确的 JWT 时，
 * 会将用户信息添加到 SecurityContext 中，从而允许后续的过滤器和处理器
 * 访问用户信息。否则不做任何处理。
 */
@Component
@Slf4j
public class JwtSpringSecurityFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private HymConfig hymConfig;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    // 如果是静态资源，直接放行
    if (StaticResources.isStaticResourceRequest(request)) {
      log.debug("请求是静态资源，放行 {}", request.getRequestURI());
      filterChain.doFilter(request, response);
      return;
    }

    // 如果没有 Authorization 头，则放行
    var header = request.getHeader(hymConfig.getJwt().getAuthHeader());
    if (header == null) {
      log.debug("请求头中没有 Authorization 头，放行 {}", request.getRequestURI());
      filterChain.doFilter(request, response);
      return;
    }

    // 如果 Authorization 头不是以 Bearer 开头，则放行
    var authType = hymConfig.getJwt().getAuthType();
    if (!header.startsWith(authType)) {
      log.debug("Authorization 头不是以 {} 开头，放行 {}", authType, request.getRequestURI());
      filterChain.doFilter(request, response);
      return;
    }

    // 解析 Bearer 的内容
    var token = header.substring(authType.length()).trim();
    var claims = jwtService.parse(token);
    if (claims == null) {
      log.debug("JWT 解析失败，放行 {}", request.getRequestURI());
      filterChain.doFilter(request, response);
      return;
    }

    // 验证用户是否存在
    log.debug("正在验证用户 {}", claims.getSubject());
    var username = claims.getSubject();
    var userDetails = userDetailsService.loadUserByUsername(username);
    if (userDetails == null) {
      log.debug("用户 {} 不存在，放行 {}", username, request.getRequestURI());
      filterChain.doFilter(request, response);
      return;
    }

    // 构建用户登录信息
    var auth = UsernamePasswordAuthenticationToken.authenticated(
      userDetails.getUsername(), null, userDetails.getAuthorities()
    );
    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    var context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);

    log.debug("用户 {} 信息获取成功", username);
    filterChain.doFilter(request, response);
  }
}
