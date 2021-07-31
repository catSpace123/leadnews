package com.feng.wemedia.controller;


import com.feng.common.pojo.Login;
import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import com.feng.wemedia.pojo.WmUserLogin;
import com.feng.wemedia.service.WmUserLoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
* <p>
* 自媒体用户登录行为信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="自媒体用户登录行为信息表",tags = "WmUserLoginController")
@RestController
@RequestMapping("/wmUserLogin")
public class WmUserLoginController extends AbstractCoreController<WmUserLogin> {

    private WmUserLoginService wmUserLoginService;

    //注入
    @Autowired
    public WmUserLoginController(WmUserLoginService wmUserLoginService) {
        super(wmUserLoginService);
        this.wmUserLoginService=wmUserLoginService;
    }



}

