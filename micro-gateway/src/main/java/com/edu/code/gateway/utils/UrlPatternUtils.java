package com.edu.code.gateway.utils;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;


public class UrlPatternUtils {
    public static boolean match(String patternUrl, String requestUrl) {
        if (StrUtil.isBlank(patternUrl) || StrUtil.isBlank(requestUrl)) {
           return false;
        }
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(patternUrl, requestUrl);
    }

    public static void main(String[] args) {

        System.out.println(match("/a/b/c/**", "/a/b/c/hello"));
    }
}
