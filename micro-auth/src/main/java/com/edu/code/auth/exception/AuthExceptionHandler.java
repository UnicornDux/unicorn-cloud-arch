package com.edu.code.auth.exception;

import com.edu.code.base.result.Result;
import com.edu.code.base.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(-1)
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * 用户不存在
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result<Void> handleUserNameNotFoundException(UsernameNotFoundException e) {
        return Result.failed(ResultCode.USER_NOT_EXIST);
    }

    /**
     * 用户名或密码错误
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidGrantException.class)
    public Result<Void> handleInvalidGrantException(InvalidGrantException e){
       return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }

    /**
     * 账户异常 (禁用，锁定，过期)
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result<Void> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
       return Result.failed(e.getMessage());
    }

    /**
     * token 无效或过期
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public Result<Void> handleInvalidTokenException(InvalidTokenException e) {
        return Result.failed(e.getMessage());
    }
}
