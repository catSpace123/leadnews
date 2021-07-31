package com.feng.admin.controller;


import com.feng.admin.pojo.AdSensitive;
import com.feng.admin.service.AdSensitiveService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 敏感词信息表 控制器</p>
*
* @since 2021-07-08
*/
@Api(value="敏感词信息表",tags = "AdSensitiveController")
@RestController
@RequestMapping("/adSensitive")
public class AdSensitiveController extends AbstractCoreController<AdSensitive> {

    private AdSensitiveService adSensitiveService;

    //注入
    @Autowired
    public AdSensitiveController(AdSensitiveService adSensitiveService) {
        super(adSensitiveService);
        this.adSensitiveService=adSensitiveService;
    }

}

