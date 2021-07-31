package com.feng.wemedia.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.wemedia.pojo.WmUserAuth;
import com.feng.wemedia.service.WmUserAuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体子账号权限信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="自媒体子账号权限信息表",tags = "WmUserAuthController")
@RestController
@RequestMapping("/wmUserAuth")
public class WmUserAuthController extends AbstractCoreController<WmUserAuth> {

    private WmUserAuthService wmUserAuthService;

    //注入
    @Autowired
    public WmUserAuthController(WmUserAuthService wmUserAuthService) {
        super(wmUserAuthService);
        this.wmUserAuthService=wmUserAuthService;
    }

}

