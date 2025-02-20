package com.hyd.hym.api;

import com.hyd.hym.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class HymControllerAdvice {

  /**
   * 处理所有未捕获的异常
   */
  @ExceptionHandler(Exception.class)
  public Response handleException(Exception ex, HttpServletRequest request) {
    log.error("发生未捕获的异常[{}]", request.getRequestURI(), ex);
    return Response.fail(ex.getMessage());
  }
}
