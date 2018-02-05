package com.cloud.exception.vo;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/25 17:47
 * @description
 */
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse() {
    }

    public String getCode() {
        return this.code;
    }

    public ErrorResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
