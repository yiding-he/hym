package com.hyd.hym.constants;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 预定义的错误码。原则上希望所有的错误都封装成 HymError 来处理
 */
@Getter
@Setter
public class HymError {

    private final int code;

    private final String message;

    private final Map<String, Object> context = new HashMap<>();

    public HymError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HymError withCause(Throwable cause) {
        return addContext("cause", cause);
    }

    public Throwable getCause() {
        return (Throwable) context.get("cause");
    }

    public HymError addContext(String key, Object value) {
        context.put(key, value);
        return this;
    }

    /**
     * 与用户登录有关的错误
     */
    public interface UserLogin {
        HymError InvalidPassword = new HymError(1001, "用户名或密码不正确");
    }
}
