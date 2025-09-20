package com.hyd.hym.jwtsecurity;

import com.hyd.hym.HymConfig;
import com.hyd.hym.constants.HymError;
import com.hyd.hym.mappers.HymUserMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 基于 JWT 的鉴权。对于需要鉴权的路径，当请求头中包含正确的 JWT 时，
 * 会将用户信息添加到 UserContextHolder 中，从而允许后续的过滤器和处理器
 * 访问用户信息。否则拒绝访问。
 */
@Component
@Slf4j
public class JwtSecurityFilter extends OncePerRequestFilter {

  @Autowired
  private HymUserMapper hymUserMapper;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private HymConfig hymConfig;

  record Context(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {
    public Context {
      if (req == null || resp == null || chain == null) {
        throw HymError.Core.NullObjectError
          .addContext("req, resp, chain")
          .toException();
      }
    }

    void accept() throws IOException, ServletException {
      Context.this.chain.doFilter(req, resp);
    }

    void deny() {
      Context.this.resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  @SuppressWarnings("NullableProblems")
  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {
    processFilter(new Context(request, response, filterChain));
  }

  private void processFilter(Context ctx) throws IOException, ServletException {
    var uri = ctx.req().getRequestURI();

    if (uri.equals("/")) {
      ctx.resp().sendRedirect("/index.html");
      return;
    }

    // 如果是静态资源，直接放行
    if (StaticResources.isStaticResourceRequest(uri)) {
      log.debug("请求是静态资源，放行 {}", uri);
      ctx.accept();
      return;
    }

    // 如果没有 Authorization 头，则放行
    var header = ctx.req().getHeader(hymConfig.getJwt().getAuthHeader());
    if (header == null) {
      log.debug("请求头中没有 Authorization 头: {}", uri);
      ctx.deny();
      return;
    }

    // 如果 Authorization 头不是以 Bearer 开头，则放行
    var authType = hymConfig.getJwt().getAuthType();
    if (!header.startsWith(authType)) {
      log.debug("Authorization 头不是以 {} 开头: {}", authType, uri);
      ctx.deny();
      return;
    }

    // 解析 Bearer 的内容
    var token = header.substring(authType.length()).trim();
    var claims = jwtService.parse(token);
    if (claims == null) {
      log.debug("JWT 解析失败: {}", uri);
      ctx.deny();
      return;
    }

    // 验证用户是否存在
    log.debug("正在验证用户 {}", claims.getSubject());
    var username = claims.getSubject();
    var user = hymUserMapper.selectForUserLogin(username);
    if (user == null) {
      log.debug("用户 {} 不存在: {}", username, uri);
      ctx.deny();
      return;
    }

    // TODO 验证 JWT 是否过期

    // TODO 构建用户登录信息
    UserContextHolder.setCurrentUser(user);

    log.debug("用户 {} 信息获取成功", username);
    ctx.accept();
  }
}
