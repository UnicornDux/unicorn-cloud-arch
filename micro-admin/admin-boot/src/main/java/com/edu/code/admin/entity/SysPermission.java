package com.edu.code.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermission {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long menuId;
    private String urlPerm;
    private List<String> roles;
    private Date createTime;
    private Date updateTime;
    private Long createBy;
    private Long updateBy;
}

