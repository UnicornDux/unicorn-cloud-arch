package com.edu.code.base.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SERVICE_NO_AUTHORITY("B00221", "服务未授权"),
    DEGRADATION ("B00220", "系统功能降级"),
    FLOW_LIMITING("B00210", "系统限流"),
    TOKEN_ACCESS_FORBIDDEN("A00215", "token禁止访问"),
    TOKEN_INVALID_OR_EXPIRED("A00214", "token非法或者失效"),
    ACCESS_UNAUTHORIZED("A00213","未授权"),
    CLIENT_AUTHENTICATION_FAILED("A00212", "客户端认证失败"),
    USER_NOT_EXIST("A00101", "用户不存在"),
    USERNAME_OR_PASSWORD_ERROR("A00100", "用户名或密码错误"),
    SYSTEM_EXECUTION_ERROR("999999", "系统异常"),
    SUCCESS("000000", "成功");


    private String code;
    private String msg;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
