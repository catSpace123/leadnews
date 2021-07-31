package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
     * @description: 用户app网关
     * @author: Cat
     * @date: 2021/7/18 21:12
     * @param: null
     * @return:
 */

@SpringBootApplication
@EnableDiscoveryClient
public class GatwayUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatwayUserApplication.class,args);
    }
}
