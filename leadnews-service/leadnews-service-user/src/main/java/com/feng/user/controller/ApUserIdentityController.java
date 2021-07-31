package com.feng.user.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUserIdentity;
import com.feng.user.service.ApUserIdentityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP身份认证信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP身份认证信息表",tags = "ApUserIdentityController")
@RestController
@RequestMapping("/apUserIdentity")
public class ApUserIdentityController extends AbstractCoreController<ApUserIdentity> {

    private ApUserIdentityService apUserIdentityService;

    //注入
    @Autowired
    public ApUserIdentityController(ApUserIdentityService apUserIdentityService) {
        super(apUserIdentityService);
        this.apUserIdentityService=apUserIdentityService;
    }

}

