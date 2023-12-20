package com.edu.code.auth.controller;


import com.edu.code.base.result.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class AuthController {

    private final TokenEndpoint tokenEndpoint;

    @PostMapping("/token")
    public Object postAccessToken(
            Principal principal, @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken auth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return Result.ok(auth2AccessToken);
    }
}
