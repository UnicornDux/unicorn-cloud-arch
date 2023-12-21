package com.edu.code.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.code.admin.dto.UserAuthDTO;
import com.edu.code.admin.entity.SysUser;
import org.apache.ibatis.annotations.Param;


public interface SysUserMapper extends BaseMapper<SysUser> {
    UserAuthDTO getByUsername(@Param("username")String username);
}
