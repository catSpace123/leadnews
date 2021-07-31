package com.feng.behaviour.controller;


import com.feng.behaviour.pojo.ApReadBehavior;
import com.feng.behaviour.service.ApReadBehaviorService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP阅读行为表 控制器</p>
* @author ljh
* @since 2021-07-20
*/
@Api(value="APP阅读行为表",tags = "ApReadBehaviorController")
@RestController
@RequestMapping("/apReadBehavior")
public class ApReadBehaviorController extends AbstractCoreController<ApReadBehavior> {

    private ApReadBehaviorService apReadBehaviorService;

    //注入
    @Autowired
    public ApReadBehaviorController(ApReadBehaviorService apReadBehaviorService) {
        super(apReadBehaviorService);
        this.apReadBehaviorService=apReadBehaviorService;
    }

}

