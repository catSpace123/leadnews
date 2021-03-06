package com.feng;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
* @author fengSir
* @version 1.0
* @date 2021/5/2 11:36
* @description 标题
* @package
*/
@SpringBootApplication
@MapperScan(basePackages = "com.feng.wemedia.mapper")   //扫描mapper接口所在的包即可
@EnableDiscoveryClient                                  //启用注册与发现
public class WemediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class,args);
    }

    /**
     * 添加一个mybatis-plus的插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}