package com.edu.code.base.consts;

public interface SecurityConstants {


    /**
     * 认证请求头 key
     */
    String AUTHORIZATION_KEY = "Authorization";

    /**
     * JWT 令牌前缀
     */
    String JWT_PREFIX = "Bearer ";

    /**
     * Basic 认证前缀
     */
    String BASIC_PREFIX = "Basic ";

    /**
     * Basic 载体 key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT ID 唯一标识
     */
    String JWT_JTI = "jti";

    /**
     * JWT ID 唯一标识
     */
    String JWT_EXP = "exp";


    /**
     * JWT 存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT 存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";

    /**
     * APP api 前缀
     */
    String APP_API_PATTEN = "/*/app-api/**";

    /**
     * 黑名单 token 前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist";

}
