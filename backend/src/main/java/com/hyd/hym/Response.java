package com.hyd.hym;

import com.hyd.hym.constants.HymError;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {

    public static final int SUCCESS = 0;

    public static final int FAIL = -1;

    public static Response success() {
        return new Response();
    }

    public static Response success(String message) {
        var response = new Response();
        response.setMessage(message);
        return response;
    }

    public static Response fail() {
        var response = new Response();
        response.setCode(FAIL);
        return response;
    }

    public static Response fail(String message) {
        var response = new Response();
        response.setCode(FAIL);
        response.setMessage(message);
        return response;
    }

    public static Response fail(HymError error) {
        var response = new Response();
        response.setCode(error.getCode());
        response.setMessage(error.getMessage());
        return response;
    }

    private int code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    public boolean isSuccess() {
        return code == SUCCESS;
    }

    public Response addData(String key, Object value) {
        data.put(key, value);
        return this;
    }
}
