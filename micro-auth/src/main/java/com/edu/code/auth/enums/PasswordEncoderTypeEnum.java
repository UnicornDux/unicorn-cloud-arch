package com.edu.code.auth.enums;


import lombok.Getter;

@Getter
public enum PasswordEncoderTypeEnum {

    BCRYPT("{bcrypt}", "BCRYPT加密"),
    NOOP("{noop}", "无加密明文");

    private final String prefix;
    private final String desc;

    private PasswordEncoderTypeEnum(String prefix, String desc){
        this.desc = desc;
        this.prefix = prefix;
    }

}
