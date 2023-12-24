package com.edu.code.admin.controller;

import com.edu.code.admin.api.OAuthClientFeignClient;
import com.edu.code.admin.dto.OAuth2ClientDTO;
import com.edu.code.admin.entity.SysOauthClient;
import com.edu.code.admin.service.SysOauthClientService;
import com.edu.code.base.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthClientController implements OAuthClientFeignClient {

    private SysOauthClientService sysOauthClientService;

    @Override
    public Result<OAuth2ClientDTO> getOAuth2ClientById(@RequestParam String clientId) {
        SysOauthClient client = sysOauthClientService.getById(clientId);
        Assert.notNull(client, "OAuth2 客户端不存在");
        OAuth2ClientDTO oAuth2ClientDTO = new OAuth2ClientDTO();
        BeanUtils.copyProperties(client, oAuth2ClientDTO);
        return Result.ok(oAuth2ClientDTO);
    }
}
