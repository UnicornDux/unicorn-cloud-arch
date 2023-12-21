package com.edu.code.auth.security;

import com.edu.code.auth.enums.PasswordEncoderTypeEnum;
import com.edu.code.web.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 取出身份，如果身份为空说明没有认证
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用httpBasic认证，httpBasic中存储了client_id和client_secret，
        // 开始认证client_id和client_secret
//        if(authentication==null){
//            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
//            if(clientDetails!=null){
//                // 密码
//                String clientSecret = clientDetails.getClientSecret();
//                return new User(
//                        username,
//                        clientSecret,
//                        AuthorityUtils.commaSeparatedStringToAuthorityList("")
//                );
//            }
//        }
        // 后面从管理端获取用户信
        SysUserDetails userDetails = loadUser(username);
        if (!userDetails.isEnabled()){
            throw new BizException("账号被禁用");
        }else if (!userDetails.isAccountNonLocked()) {
            throw new BizException("账号被锁定");
        }else if (!userDetails.isAccountNonExpired()) {
            throw new BizException("账号已经过期");
        }
        return userDetails;
    }

    private SysUserDetails loadUser(String username) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        return SysUserDetails.builder()
                .userId(1L)
                .username(username)
                .enabled(true)
                .authorities(authorities)
                .password(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + new BCryptPasswordEncoder().encode("123456789"))
                .build();

    }
}
