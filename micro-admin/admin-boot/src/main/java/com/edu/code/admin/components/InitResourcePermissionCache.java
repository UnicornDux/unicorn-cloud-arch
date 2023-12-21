package com.edu.code.admin.components;

import com.edu.code.admin.service.SysPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitResourcePermissionCache implements CommandLineRunner {

    private SysPermissionService sysPermissionService;

    @Override
    public void run(String... args) throws Exception {
       sysPermissionService.refreshPermRolesRules();
    }
}
