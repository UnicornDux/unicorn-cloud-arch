package com.edu.code.web.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
public class FeignConfig {


    @Value("${spring.application.name")
    private String applicationName;


    /**
     * 让 DispatcherServlet 向子线程传递 RequestContext
     * @param servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherRegistration(DispatcherServlet servlet) {
        servlet.setThreadContextInheritable(true);
        return new ServletRegistrationBean<>(servlet, "/**");
    }

    /**
     * 覆写拦截器, 再 feign 发送请求前取出原来的 header 并转发
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
       return (requestTemplate) -> {
           ServletRequestAttributes attributes =
                   (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
           assert attributes != null;
           HttpServletRequest request = attributes.getRequest();
           // 获取请求头
           Enumeration<String> headerNames = request.getHeaderNames();
           if (headerNames != null) {
               while(headerNames.hasMoreElements()){
                   String name = headerNames.nextElement();
                   String value = request.getHeader(name);
                   // 将请求头保存到模板中
                   if (!name.equalsIgnoreCase("serviceName")){
                       requestTemplate.header(name, value);
                   }
               }
               requestTemplate.header("serviceName", applicationName);
           }
       };
    }
}
