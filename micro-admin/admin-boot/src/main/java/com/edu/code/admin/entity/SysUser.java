package com.edu.code.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private Integer gender;
    private String avatar;
    private String mobile;
    private String email;
    private Integer status;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;
    private Long createBy;
    private Long updateBy;
}
