package com.cloud.exception.handler;

import com.cloud.exception.vo.ErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/25 17:45
 * @description  统一异常处理
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    private static final String SYS_EXCEPTION_CODE = "000000";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        ErrorResponse responseData = new ErrorResponse();
        String message = exception.getMessage();
        ResponseEntity<ErrorResponse> responseEntity = null;
        responseData.setCode(SYS_EXCEPTION_CODE);
        if(!StringUtils.isBlank(message)) {
            responseData.setMessage(message);
        } else {
            responseData.setMessage(exception.toString());
        }
        responseEntity  =  new ResponseEntity<ErrorResponse>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
