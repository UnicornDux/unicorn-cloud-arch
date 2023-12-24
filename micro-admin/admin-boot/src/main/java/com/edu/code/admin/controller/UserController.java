package com.edu.code.admin.controller;

import com.edu.code.admin.api.UserFeignClient;
import com.edu.code.admin.dto.UserAuthDTO;
import com.edu.code.admin.service.SysUserService;
import com.edu.code.base.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserFeignClient {

    private final SysUserService sysUserService;
    @Override
    public Result<UserAuthDTO> getUserByUsername(@PathVariable String username) {
        UserAuthDTO user = sysUserService.getByUsername(username);
        return Result.ok(user);
    }

}
