package com.hyd.hym.api;

import com.hyd.hym.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class HymApiControllerAdvice {

  /**
   * 处理所有未捕获的异常
   */
  @ExceptionHandler(value = Exception.class)
  public Response handleException(
    Exception ex, HttpServletRequest request, HttpServletResponse response
  ) throws IOException {
    if (ex instanceof NoResourceFoundException ne) {
      log.debug("资源文件访问失败: {}", ne.getMessage());
      response.sendError(HttpStatus.NOT_FOUND.value());
      return null;
    } else {
      log.error("发生未捕获的异常[{}]", request.getRequestURI(), ex);
      return Response.error(ex.getMessage());
    }
  }
}
