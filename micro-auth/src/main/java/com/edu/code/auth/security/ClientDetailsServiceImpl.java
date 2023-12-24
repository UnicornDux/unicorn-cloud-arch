package com.edu.code.auth.security;

import com.edu.code.admin.api.OAuthClientFeignClient;
import com.edu.code.admin.dto.OAuth2ClientDTO;
import com.edu.code.auth.enums.PasswordEncoderTypeEnum;
import com.edu.code.base.result.Result;
import com.edu.code.base.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

// @Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OAuthClientFeignClient oAuthClientFeignClient;

    @Override
    // @Cacheable(cacheNames = "auth", key = "'oauth-client:' + #clientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        // 后续通过接口查询
        Result<OAuth2ClientDTO> result = oAuthClientFeignClient.getOAuth2ClientById(clientId);
        if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
            OAuth2ClientDTO client = result.getData();
            BaseClientDetails clientDetails = new BaseClientDetails(
                    client.getClientId(),
                    client.getResourceIds(),
                    client.getScope(),
                    client.getAuthorizedGrantTypes(),
                    client.getAuthorities(),
                    client.getWebServerRedirectUri()
            );
            clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
            clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
            clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
            return clientDetails;
        }else {
            throw new NoSuchClientException(result.getMsg());
        }
    }
}
