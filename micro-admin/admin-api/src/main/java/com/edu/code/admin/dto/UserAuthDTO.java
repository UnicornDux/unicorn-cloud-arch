package com.edu.code.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserAuthDTO {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态 1-有效, 0-禁用
     */
    private Integer status;

    /**
     * 用户角色编码集合 [ "ROOT", "ADMIN" ]
     */
    private List<String> roles;

}
