package com.lei.tang.frame.exception;

import domain.CommonResponse;
import domain.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tanglei
 * @date 18/10/12
 * <p>
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public CommonResponse illegalArgsExceptionHandler(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        CommonResponse response = new CommonResponse();
        response.setRetMsg(e.getMessage());
        response.setRetCode(ResponseCode.INVALID_PARAM);
        return response;
    }

    /**
     * 处理没有权限异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public CommonResponse unauthorizedExceptionHandler(UnauthorizedException e) {
        log.error(e.getMessage(), e);
        CommonResponse response = new CommonResponse();
        response.setRetCode(ResponseCode.NO_PERMISSION);
        response.setRetMsg("未获授权");
        return response;
    }


    /**
     * 处理认证异常
     * <p>
     * AuthenticationException:所有认证时异常的父类.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public CommonResponse authenticationExceptionHandler(AuthenticationException e) {
        log.error(e.getMessage(), e);
        CommonResponse response = new CommonResponse();
        response.setRetCode(ResponseCode.AUTH_FAILURE);
        String message = null;
        if (e instanceof UnknownAccountException) {
            message = "账号不存在";
        } else if (e instanceof IncorrectCredentialsException) {
            message = "密码错误";
        } else {
            message = e.getMessage();
        }
        response.setRetMsg(message);
        return response;
    }


    @ExceptionHandler(value = Exception.class)
    public CommonResponse defaultExceptionHandler(Exception e) {
        log.error("Encounter exception.", e);
        CommonResponse response = new CommonResponse();
        response.setRetCode(ResponseCode.INTERNAL_ERROR);
        response.setRetMsg(e.getMessage());
        return response;
    }
}
