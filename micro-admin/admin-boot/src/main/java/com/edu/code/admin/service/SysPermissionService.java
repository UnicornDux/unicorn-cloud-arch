package com.edu.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.code.admin.entity.SysPermission;

import java.util.List;


public interface SysPermissionService  extends IService<SysPermission> {
    boolean refreshPermRolesRules();

    List<SysPermission> listPermission();

}
