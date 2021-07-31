package com.feng.user.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUserInfo;
import com.feng.user.service.ApUserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP用户信息表",tags = "ApUserInfoController")
@RestController
@RequestMapping("/apUserInfo")
public class ApUserInfoController extends AbstractCoreController<ApUserInfo> {

    private ApUserInfoService apUserInfoService;

    //注入
    @Autowired
    public ApUserInfoController(ApUserInfoService apUserInfoService) {
        super(apUserInfoService);
        this.apUserInfoService=apUserInfoService;
    }

}

