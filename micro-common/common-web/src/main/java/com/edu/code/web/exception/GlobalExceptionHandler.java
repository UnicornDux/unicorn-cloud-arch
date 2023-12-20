package com.edu.code.web.exception;

import com.edu.code.base.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public <T> Result<T> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常: {}", e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

}
