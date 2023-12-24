package com.edu.code.auth.security;

import com.edu.code.admin.api.UserFeignClient;
import com.edu.code.admin.dto.UserAuthDTO;
import com.edu.code.auth.enums.PasswordEncoderTypeEnum;
import com.edu.code.base.result.Result;
import com.edu.code.base.result.ResultCode;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserDetailServiceImpl implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 后面从管理端获取用户信
        Result<UserAuthDTO> result = userFeignClient.getUserByUsername(username);
        SysUserDetails sysUserDetails = null;
        if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
            UserAuthDTO user = result.getData();
            if (!Objects.isNull(user)) {
                sysUserDetails  = SysUserDetails.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .authorities(handleRoles(user.getRoles()))
                        .enabled(user.getStatus() == 1)
                        .password(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + user.getPassword())
                        .build();
            }
        }
        if (Objects.isNull(sysUserDetails)) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }
        if (!sysUserDetails.isEnabled()){
            throw new BizException("账号被禁用");
        }else if (!sysUserDetails.isAccountNonLocked()) {
            throw new BizException("账号被锁定");
        }else if (!sysUserDetails.isAccountNonExpired()) {
            throw new BizException("账号已经过期");
        }
        return sysUserDetails;
    }

    // 将角色转换为对应的权限对象
    private Collection<SimpleGrantedAuthority> handleRoles(List<String> roles) {
          return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
