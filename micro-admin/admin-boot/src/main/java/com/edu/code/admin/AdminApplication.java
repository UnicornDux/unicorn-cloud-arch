package com.edu.code.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AdminApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
