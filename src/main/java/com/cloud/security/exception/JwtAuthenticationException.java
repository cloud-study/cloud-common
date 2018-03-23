package com.cloud.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/3/2 15:36
 * @name
 * @description
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
