package com.feng.user.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUser;
import com.feng.user.service.ApUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户信息表 控制器</p>
* @author
* @since 2021-07-09
*/
@Api(value="APP用户信息表",tags = "ApUserController")
@RestController
@RequestMapping("/apUser")
public class ApUserController extends AbstractCoreController<ApUser> {

    private ApUserService apUserService;

    //注入
    @Autowired
    public ApUserController(ApUserService apUserService) {
        super(apUserService);
        this.apUserService=apUserService;
    }

}

