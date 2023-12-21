package com.edu.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.code.admin.dto.UserAuthDTO;
import com.edu.code.admin.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    UserAuthDTO getByUsername(String username);

}
