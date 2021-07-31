package com.feng.admin.controller;


import com.feng.admin.pojo.AdUserOpertion;
import com.feng.admin.service.AdUserOpertionService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 管理员操作行为信息表 控制器</p>
* @author ljh
* @since 2021-07-08
*/
@Api(value="管理员操作行为信息表",tags = "AdUserOpertionController")
@RestController
@RequestMapping("/adUserOpertion")
public class AdUserOpertionController extends AbstractCoreController<AdUserOpertion> {

    private AdUserOpertionService adUserOpertionService;

    //注入
    @Autowired
    public AdUserOpertionController(AdUserOpertionService adUserOpertionService) {
        super(adUserOpertionService);
        this.adUserOpertionService=adUserOpertionService;
    }

}

