package com.feng.article.controller;


import com.feng.article.pojo.ApDynamic;
import com.feng.article.service.ApDynamicService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户动态信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP用户动态信息表",tags = "ApDynamicController")
@RestController
@RequestMapping("/apDynamic")
public class ApDynamicController extends AbstractCoreController<ApDynamic> {

    private ApDynamicService apDynamicService;

    //注入
    @Autowired
    public ApDynamicController(ApDynamicService apDynamicService) {
        super(apDynamicService);
        this.apDynamicService=apDynamicService;
    }

}

