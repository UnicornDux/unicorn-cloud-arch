package com.edu.code.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main( String[] args ) {
        ConfigurableApplicationContext run = SpringApplication.run(GatewayApplication.class, args);
    }
}
