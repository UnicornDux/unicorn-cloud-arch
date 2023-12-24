package com.edu.code.admin.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SysPermission {
    private String urlPerm;
    private List<String> roles;
}

