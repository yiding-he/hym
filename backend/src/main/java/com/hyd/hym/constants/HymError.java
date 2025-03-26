package com.hyd.hym.constants;

import com.hyd.hym.HymException;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 预定义的错误码。原则上希望所有的错误都封装成 HymError 来处理。
 * message 中可以使用 {key} 来表示上下文变量的值。例如：
 * <pre>
 *   // 预定义的消息模板为 "用户 {username} 不存在"
 *   var err = HymError.UserLogin.UserNameNotFound.addContext("username", "admin2");
 *   System.out.println(err.getMessage());
 * </pre>
 * 将会输出 "用户 admin2 不存在"。
 */
@Getter
@Setter
public class HymError {

  public static final String CAUSE_KEY = "__cause__";

  private final int code;

  private final String message;

  private final Map<String, Object> context = new HashMap<>();

  public HymError(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public HymError(int code, String message, Map<String, Object> context) {
    this.code = code;
    this.message = message;
    this.context.putAll(context);
  }

  public HymError withCause(Throwable cause) {
    return addContext(CAUSE_KEY, cause);
  }

  public Throwable getCause() {
    return (Throwable) context.get(CAUSE_KEY);
  }

  public HymError addContext(String key, Object value) {
    var newInstance = new HymError(code, message, context);
    newInstance.context.put(key, value);
    return newInstance;
  }

  public HymError addContext(Object value) {
    return addContext("", value);
  }

  public HymException toException() {
    return HymException.create(this);
  }

  public String getMessage() {
    var msg = this.message;
    for (String key : context.keySet()) {
      if (key.startsWith("__")) {
        continue;
      }
      msg = msg.replace("{" + key + "}", String.valueOf(context.get(key)));
    }
    return msg;
  }

  /// /////////////////////////////////

  /**
   * 通用错误
   */
  public interface Common {
    HymError UnknownError = new HymError(1, "未知错误");
  }

  /**
   * 与用户登录有关的错误
   */
  public interface UserLogin {
    HymError UserNotFound = new HymError(1000, "用户不存在");
    HymError UserNameNotFound = new HymError(1001, "用户 {username} 不存在");
    HymError InvalidPassword = new HymError(1002, "用户名或密码不正确");
    HymError UserDisabled = new HymError(1003, "用户已被禁用");
  }

  /**
   * 与一些底层机制相关的错误
   */
  public interface Core {
    HymError ParseBeanMethodError = new HymError(2000, "解析 Bean 方法出错");
    HymError ParseQueryOperatorError = new HymError(2001, "解析查询操作符出错: {operator}");
    HymError NullObjectError = new HymError(2002, "{} 不能为空");
  }
}
