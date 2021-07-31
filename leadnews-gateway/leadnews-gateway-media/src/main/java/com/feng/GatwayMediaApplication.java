package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 自媒体网关
 */
@SpringBootApplication
@EnableDiscoveryClient  //注册与发现
public class GatwayMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatwayMediaApplication.class,args);
    }
}
