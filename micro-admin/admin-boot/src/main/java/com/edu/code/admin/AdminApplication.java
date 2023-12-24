package com.edu.code.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.edu.code.**.mapper")
@EnableFeignClients
public class AdminApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
