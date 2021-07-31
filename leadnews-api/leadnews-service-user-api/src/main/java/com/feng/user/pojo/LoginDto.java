package com.feng.user.pojo;

import lombok.Data;

/**
 *
     * @description: 用来接收app用户端的登录请求
     * @author: Cat
     * @date: 2021/7/18 19:28
     * @param: null
     * @return:
 */


@Data
public class LoginDto {
    /**
    设备id
     */

    private Integer equipmentId;

    /**
         0 表示 不登录先看看   1表示 需要登录 默认为 1
     */
    private Integer flag = 1;

    /**
        手机号
     */
    private String phone;

    /**
    密码
     */
    private String password;
}