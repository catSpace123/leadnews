package com.feng.user.controller;

import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.Result;
import com.feng.user.pojo.LoginDto;
import com.feng.user.service.ApUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
     * @description:  用户登录
     * @author: Cat
     * @date: 2021/7/18 19:32
     * @param: null
     * @return:
 */
@RestController
@RequestMapping("/app")
@Api(tags = "用户登录",description = "用来用户登录")
public class AppLoginController {

    @Autowired
    private ApUserService apUserService;

    @PostMapping("/loginDto")
    public Result appLogin(@RequestBody LoginDto loginDto){

        if(loginDto == null){
            throw new LeadnewsException("状态不正确");
        }

         Map<String,Object> map = apUserService.appLogin(loginDto);
        return Result.ok(map);
    }
}
