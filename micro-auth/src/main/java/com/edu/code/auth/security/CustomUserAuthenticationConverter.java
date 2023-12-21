package com.edu.code.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    private final UserDetailsService userDetailsService;

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        String name = authentication.getName();
        response.put("user_name", name);

        Object principal = authentication.getPrincipal();
        SysUserDetails sysUserDetails;
        if (principal instanceof SysUserDetails) {
            sysUserDetails = (SysUserDetails) principal;
        }else {
            UserDetails userDetails = userDetailsService.loadUserByUsername(name);
            sysUserDetails = (SysUserDetails) userDetails;
        }
        response.put("userId", sysUserDetails.getUserId());
        response.put("username", sysUserDetails.getUsername());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()){
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
