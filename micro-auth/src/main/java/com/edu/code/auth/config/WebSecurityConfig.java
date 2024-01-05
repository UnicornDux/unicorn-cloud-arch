package com.edu.code.auth.config;

import cn.hutool.json.JSONUtil;
import com.edu.code.base.result.Result;
import com.edu.code.base.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.PrintWriter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService sysUserDetailsService;

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/auth/**");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/public-key").permitAll()
                .anyRequest().authenticated();
    }


    /**
     * 认证管理对象
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 添加自定义认证器
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * 设置默认的用户名密码认证授权提供者
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(sysUserDetailsService);
       provider.setPasswordEncoder(passwordEncoder());
       provider.setHideUserNotFoundExceptions(false);
       return provider;
    }

    /**
     * 密码管理器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * 自定义认证失败时返回的信息
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
       return (request, response, authException) -> {
           response.setStatus(HttpStatus.OK.value());
           response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
           response.setHeader("Access-Control-Allow-Origin", "*");
           response.setHeader("Cache-Control", "no-cache");
           Result<Void> result = Result.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
           PrintWriter writer = response.getWriter();
           writer.print(JSONUtil.toJsonStr(result));
           writer.flush();
       };
    }
}
