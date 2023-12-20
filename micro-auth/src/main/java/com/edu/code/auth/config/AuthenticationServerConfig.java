package com.edu.code.auth.config;

import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;
import cn.hutool.core.collection.CollectionUtil;
import com.edu.code.auth.security.SysUserDetailServiceImpl;
import com.edu.code.auth.security.SysUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.Jar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.web.servlet.oauth2.login.TokenEndpointDsl;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.*;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final ClientDetailsService clientDetailsService;

    /**
     * Oauth2 客户端
     * @param clients the client details configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置授权 {authorization} 和令牌的访问端点和令牌服务 {token service}
     * @param endpoints the endpoints configurer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // Token 增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        // 获取原有默认的授权模式(授权码模式，密码模式，客户端模式，简化模式) 的授权者
        List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));
        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);

        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .tokenGranter(compositeTokenGranter)
                .reuseRefreshTokens(true)
                .tokenServices(tokenServices(endpoints));
    }

    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints){
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        chain.setTokenEnhancers(tokenEnhancers);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(chain);
        return tokenServices;
    }

    /**
     * jwt 内容增强
     * @return
     */

    @Bean
    public TokenEnhancer tokenEnhancer(){
       return (accessToken, authentication) -> {
           Map<String, Object> additionalInfo = new HashMap<>();
           Object principal = authentication.getUserAuthentication().getPrincipal();
           if (principal instanceof SysUserDetails){
               SysUserDetails sysUserDetails = (SysUserDetails) principal;
               additionalInfo.put("userId", sysUserDetails.getUserId());
               additionalInfo.put("username", sysUserDetails.getUsername());
               ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
           }
           return accessToken;
       };
    }

    /**
     * 使用非对称加密对 token 进行签名
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }


    /**
     * 密钥库中获取密钥对(公钥, 私钥)
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        return factory.getKeyPair("jwt", "123456".toCharArray());
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }
}
