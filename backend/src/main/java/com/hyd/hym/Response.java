package com.hyd.hym;

import com.hyd.hybatis.pagination.PageHelperPage;
import com.hyd.hym.constants.HymError;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {

  public static final int SUCCESS = 0;

  public static final int FAIL = -1;

  public static Response ok() {
    return new Response();
  }

  public static Response ok(String message) {
    var response = new Response();
    response.setMessage(message);
    return response;
  }

  public static Response ok(Result<?> result, String key) {
    return ok().addData(key, result.getData());
  }

  public static Response error() {
    var response = new Response();
    response.setCode(FAIL);
    return response;
  }

  public static Response error(String message) {
    return error(FAIL, message);
  }

  public static Response error(int code, String message) {
    var response = new Response();
    response.setCode(code);
    response.setMessage(message);
    return response;
  }

  public static Response error(HymError error) {
    var response = new Response();
    response.setCode(error.getCode());
    response.setMessage(error.getMessage());
    return response;
  }

  public static Response error(Result<?> result) {
    return error(result.getError().getCode(), result.getError().getMessage());
  }

  private int code;

  private String message;

  private Map<String, Object> data = new HashMap<>();

  public boolean isOk() {
    return code == SUCCESS;
  }

  public Response addData(String key, Object value) {
    data.put(key, value);
    return this;
  }

  public Response addPage(PageHelperPage<?> page) {
    return this
      .addData("rows", page.getList())
      .addData("total", page.getTotal())
      .addData("pageSize", page.getPageSize())
      .addData("pageIndex", page.getPageNum())
      .addData("totalPage", page.getPages());
  }

  @SuppressWarnings("unchecked")
  public <T> T getData(String key) {
    return (T) data.get(key);
  }

  public <T> T getData(String key, Class<T> type) {
    return type.cast(data.get(key));
  }
}
