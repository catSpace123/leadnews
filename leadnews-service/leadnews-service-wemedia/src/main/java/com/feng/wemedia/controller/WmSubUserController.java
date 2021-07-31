package com.feng.wemedia.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.wemedia.pojo.WmSubUser;
import com.feng.wemedia.service.WmSubUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体子账号信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="自媒体子账号信息表",tags = "WmSubUserController")
@RestController
@RequestMapping("/wmSubUser")
public class WmSubUserController extends AbstractCoreController<WmSubUser> {

    private WmSubUserService wmSubUserService;

    //注入
    @Autowired
    public WmSubUserController(WmSubUserService wmSubUserService) {
        super(wmSubUserService);
        this.wmSubUserService=wmSubUserService;
    }

}

