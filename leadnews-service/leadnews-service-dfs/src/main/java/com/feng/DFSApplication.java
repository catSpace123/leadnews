package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient   //注册与发现
public class DFSApplication {
    public static void main(String[] args) {
        SpringApplication.run(DFSApplication.class,args);
    }
}
