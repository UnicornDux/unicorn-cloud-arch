package com.edu.code.auth.security;

import com.edu.code.auth.enums.PasswordEncoderTypeEnum;
import com.edu.code.web.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service("sysUserDetailsService")
@RequiredArgsConstructor
public class SysUserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
                .password(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + new BCryptPasswordEncoder().encode("123456"))
                .build();

    }



}
