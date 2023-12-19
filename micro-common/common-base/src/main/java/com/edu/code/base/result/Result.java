package com.edu.code.base.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private String code;

    private T data;

    private String msg;
    private Integer total;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
       ResultCode code = ResultCode.SUCCESS;
       if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
           code = ResultCode.SYSTEM_EXECUTION_ERROR;
       }
       return result(code, data);
    }

    private static <T> Result<T> result(ResultCode code, T data) {
        Result<T> result = new Result<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> ok(T data, Long total) {
       Result<T>  result = new Result<>();
       result.setCode(ResultCode.SUCCESS.getCode());
       result.setMsg(ResultCode.SUCCESS.getMsg());
       result.setData(data);
       result.setTotal(total.intValue());
       return result;
    }



}
