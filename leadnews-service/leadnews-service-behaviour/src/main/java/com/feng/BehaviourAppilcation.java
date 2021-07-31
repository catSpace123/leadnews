package com.feng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 行为微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.feng.*.mapper")
public class BehaviourAppilcation {

    public static void main(String[] args) {
        SpringApplication.run(BehaviourAppilcation.class,args);
    }
}
