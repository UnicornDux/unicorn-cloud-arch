package com.edu.code.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.code.admin.entity.SysOauthClient;
import com.edu.code.admin.mapper.SysOauthClientMapper;
import com.edu.code.admin.service.SysOauthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper,SysOauthClient> implements SysOauthClientService {
}
