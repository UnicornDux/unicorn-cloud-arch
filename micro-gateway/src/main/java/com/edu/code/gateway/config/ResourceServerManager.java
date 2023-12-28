package com.edu.code.gateway.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import com.edu.code.base.consts.GlobalConstants;
import com.edu.code.base.consts.SecurityConstants;
import com.edu.code.gateway.utils.UrlPatternUtils;
import com.edu.code.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "security")
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisUtils redisUtils;

    // 白名单 不使用 security 自带的
    // 抽离到配置文件,从配置中心中以支持动态修改
    @Setter
    private List<String> ignoreUrls;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        if (request.getMethod() == HttpMethod.OPTIONS) {
           return Mono.just(new AuthorizationDecision(true));
        }
        AntPathMatcher matcher = new AntPathMatcher();
        String method = request.getMethodValue();
        String path = request.getURI().getPath();

        // 跳过 token 校验，放在这里去做是为了能动态刷新
        if (skipValid(path)) {
           return Mono.just(new AuthorizationDecision(true));
        }

        // 如果 token 为空，或者 token 不合法则进行拦截
        String token = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token)|| StrUtil.startWithIgnoreCase(token, SecurityConstants.JWT_PREFIX)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 从 redis 中获取资源权限
        String restfulPath = method + ":" + path; // RESTful 接口权限设计
        Map<String, Object> urlPermRules = (Map<String, Object>) redisUtils.entries(GlobalConstants.URL_PERM_ROLES_KEY);
        List<String> authorizedRoles = new ArrayList<>(); // 拥有访问权限的角色
        AtomicBoolean requireCheck = new AtomicBoolean(false); // 是否需要鉴权, 默认未设置拦截规则不需要鉴权

        // 从 redis 中获取资源权限
        urlPermRules.forEach((perm, value) -> {
            if (matcher.match(perm, restfulPath)) {
                List<String> roles = Convert.toList(String.class, value);
                authorizedRoles.addAll(Convert.toList(String.class, roles));
                if (!requireCheck.get()) {
                    requireCheck.set(true);
                }
            }
        });

        // 如果资源不需要权限，则直接返回授权成功
        if (!requireCheck.get()) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断 jwt 中携带的用户角色是否有权限访问
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any( authority -> {
                    String roleCode = authority.substring(SecurityConstants.AUTHORITY_PREFIX.length());
                    return CollectionUtil.isNotEmpty(authorizedRoles) && authorizedRoles.contains(roleCode);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 跳过校验
     */
    private boolean skipValid(String path) {
        return ignoreUrls.stream().anyMatch((item) -> UrlPatternUtils.match(item, path));
    }

}
