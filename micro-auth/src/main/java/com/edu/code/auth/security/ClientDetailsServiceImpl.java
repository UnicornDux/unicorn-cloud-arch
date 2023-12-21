package com.edu.code.auth.security;

import com.edu.code.auth.enums.PasswordEncoderTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

// @Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Override
    // @Cacheable(cacheNames = "auth", key = "'oauth-client:' + #clientId")
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {

        // 后续通过接口查询
        BaseClientDetails clientDetails = new BaseClientDetails(
                "ancion",
                "",
                "all",
                "password,client_credentials,refresh_token,authorization_code",
                "",
                "https://baidu.com"
        );

        clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + "ancion");
        clientDetails.setAccessTokenValiditySeconds(3600);
        clientDetails.setRefreshTokenValiditySeconds(36000000);
        return clientDetails;
    }
}
