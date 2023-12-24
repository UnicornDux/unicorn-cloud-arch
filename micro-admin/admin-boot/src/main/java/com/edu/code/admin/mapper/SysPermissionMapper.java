package com.edu.code.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.code.admin.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> listPermRoles();
}
