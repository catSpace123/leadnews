package com.feng.user.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUserFan;
import com.feng.user.service.ApUserFanService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户粉丝信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP用户粉丝信息表",tags = "ApUserFanController")
@RestController
@RequestMapping("/apUserFan")
public class ApUserFanController extends AbstractCoreController<ApUserFan> {

    private ApUserFanService apUserFanService;

    //注入
    @Autowired
    public ApUserFanController(ApUserFanService apUserFanService) {
        super(apUserFanService);
        this.apUserFanService=apUserFanService;
    }

}

