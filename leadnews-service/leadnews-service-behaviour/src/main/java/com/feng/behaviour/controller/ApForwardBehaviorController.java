package com.feng.behaviour.controller;


import com.feng.behaviour.pojo.ApForwardBehavior;
import com.feng.behaviour.service.ApForwardBehaviorService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP转发行为表 控制器</p>
* @author ljh
* @since 2021-07-20
*/
@Api(value="APP转发行为表",tags = "ApForwardBehaviorController")
@RestController
@RequestMapping("/apForwardBehavior")
public class ApForwardBehaviorController extends AbstractCoreController<ApForwardBehavior> {

    private ApForwardBehaviorService apForwardBehaviorService;

    //注入
    @Autowired
    public ApForwardBehaviorController(ApForwardBehaviorService apForwardBehaviorService) {
        super(apForwardBehaviorService);
        this.apForwardBehaviorService=apForwardBehaviorService;
    }

}

