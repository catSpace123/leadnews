package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fengStr
 * @version 1.0
 * @date 2021/7/9 10:21
 * @description 标题
 * @package com.feng
 */
@SpringBootApplication
@EnableDiscoveryClient  //开启服务注册与发现
public class GatewayAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayAdminApplication.class,args);
    }
}
