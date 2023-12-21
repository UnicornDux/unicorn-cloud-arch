package com.edu.code.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.code.admin.entity.SysPermission;
import com.edu.code.admin.mapper.SysPermissionMapper;
import com.edu.code.admin.service.SysPermissionService;
import com.edu.code.base.consts.GlobalConstants;
import com.edu.code.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    private final RedisUtils redisUtils;

    @Override
    public boolean refreshPermRolesRules() {
        redisUtils.del(GlobalConstants.URL_PERM_ROLES_KEY);
        List<SysPermission> permissions = this.listPermRoles();
        if (CollectionUtil.isNotEmpty(permissions)) {
            // 初始化 URL - 角色规则
            List<SysPermission> urlPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getUrlPerm()))
                    .collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(urlPermList)){
                Map<String, List<String>> urlPermRoles = new HashMap<>();
                urlPermList.forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    urlPermRoles.put(perm, roles);
                });
                redisUtils.putAll(GlobalConstants.URL_PERM_ROLES_KEY, urlPermRoles);
            }
        }
        return true;
    }

    @Override
    public List<SysPermission> listPermission() {
        return null;
    }
}
