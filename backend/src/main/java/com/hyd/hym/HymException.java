package com.hyd.hym;

import com.hyd.hym.constants.HymError;

/**
 * 通用异常封装
 */
public class HymException extends RuntimeException {

    public static HymException create(String message) {
        return new HymException(message);
    }

    public static HymException create(String message, Throwable cause) {
        return new HymException(message, cause);
    }

    public static HymException create(HymError error) {
        return new HymException(error.getMessage(), error.getCause());
    }

    /// //////////////////////////////////////////

    public HymException() {
    }

    public HymException(String message) {
        super(message);
    }

    public HymException(String message, Throwable cause) {
        super(message, cause);
    }

    public HymException(Throwable cause) {
        super(cause);
    }
}
