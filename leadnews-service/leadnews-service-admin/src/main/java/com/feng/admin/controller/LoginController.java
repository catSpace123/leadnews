package com.feng.admin.controller;

import com.feng.admin.service.AdUserService;
import com.feng.common.pojo.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "后台登录",description = "输入用户名密码",value = "LoginController")
public class LoginController {


    @Autowired
    private AdUserService adUserService;

    /**
     * 用户登录
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "后台登录",notes = "登录")
    public Map<String,Object> login(@RequestBody Login login) {


        Map<String,Object> map = adUserService.login(login);

        return map;
    }


}
