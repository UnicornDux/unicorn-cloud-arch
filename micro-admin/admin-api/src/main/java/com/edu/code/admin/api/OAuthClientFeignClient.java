package com.edu.code.admin.api;

import com.edu.code.admin.dto.OAuth2ClientDTO;
import com.edu.code.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "micro-admin", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @GetMapping("/api/oauth-clients/getOAuth2ClientById")
    Result<OAuth2ClientDTO> getOAuth2ClientById(@RequestParam String clientId);

}
