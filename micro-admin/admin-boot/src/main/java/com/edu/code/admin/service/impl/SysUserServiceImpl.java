package com.edu.code.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.code.admin.dto.UserAuthDTO;
import com.edu.code.admin.entity.SysUser;
import com.edu.code.admin.mapper.SysUserMapper;
import com.edu.code.admin.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public UserAuthDTO getByUsername(String username) {
        return this.baseMapper.getByUsername(username);
    }
}
