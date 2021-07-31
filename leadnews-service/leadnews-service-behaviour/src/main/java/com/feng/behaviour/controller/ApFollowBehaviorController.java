package com.feng.behaviour.controller;


import com.feng.behaviour.pojo.ApFollowBehavior;
import com.feng.behaviour.service.ApFollowBehaviorService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP关注行为表 控制器</p>
* @author ljh
* @since 2021-07-20
*/
@Api(value="APP关注行为表",tags = "ApFollowBehaviorController")
@RestController
@RequestMapping("/apFollowBehavior")
public class ApFollowBehaviorController extends AbstractCoreController<ApFollowBehavior> {

    private ApFollowBehaviorService apFollowBehaviorService;

    //注入
    @Autowired
    public ApFollowBehaviorController(ApFollowBehaviorService apFollowBehaviorService) {
        super(apFollowBehaviorService);
        this.apFollowBehaviorService=apFollowBehaviorService;
    }

}

