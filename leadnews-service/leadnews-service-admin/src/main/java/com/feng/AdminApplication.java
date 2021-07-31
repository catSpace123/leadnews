package com.feng;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 后台管理启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.feng.admin.mapper")
@EnableDiscoveryClient  //启用注册与发现
@EnableFeignClients("com.feng.*.feign")
public class AdminApplication {


    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }

    //mybatisplus分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
