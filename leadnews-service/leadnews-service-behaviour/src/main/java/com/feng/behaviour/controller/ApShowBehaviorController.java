package com.feng.behaviour.controller;


import com.feng.behaviour.pojo.ApShowBehavior;
import com.feng.behaviour.service.ApShowBehaviorService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP文章展现行为表 控制器</p>
* @author ljh
* @since 2021-07-20
*/
@Api(value="APP文章展现行为表",tags = "ApShowBehaviorController")
@RestController
@RequestMapping("/apShowBehavior")
public class ApShowBehaviorController extends AbstractCoreController<ApShowBehavior> {

    private ApShowBehaviorService apShowBehaviorService;

    //注入
    @Autowired
    public ApShowBehaviorController(ApShowBehaviorService apShowBehaviorService) {
        super(apShowBehaviorService);
        this.apShowBehaviorService=apShowBehaviorService;
    }

}

