package com.feng.admin.controller;


import com.feng.admin.pojo.AdUserLogin;
import com.feng.admin.service.AdUserLoginService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 管理员登录行为信息表 控制器</p>
* @author ljh
* @since 2021-07-08
*/
@Api(value="管理员登录行为信息表",tags = "AdUserLoginController")
@RestController
@RequestMapping("/adUserLogin")
public class AdUserLoginController extends AbstractCoreController<AdUserLogin> {

    private AdUserLoginService adUserLoginService;

    //注入
    @Autowired
    public AdUserLoginController(AdUserLoginService adUserLoginService) {
        super(adUserLoginService);
        this.adUserLoginService=adUserLoginService;
    }

}

