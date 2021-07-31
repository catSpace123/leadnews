package com.feng.admin.controller;


import com.feng.admin.pojo.AdRecommendStrategy;
import com.feng.admin.service.AdRecommendStrategyService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 推荐策略信息表 控制器</p>
* @author ljh
* @since 2021-07-08
*/
@Api(value="推荐策略信息表",tags = "AdRecommendStrategyController")
@RestController
@RequestMapping("/adRecommendStrategy")
public class AdRecommendStrategyController extends AbstractCoreController<AdRecommendStrategy> {

    private AdRecommendStrategyService adRecommendStrategyService;

    //注入
    @Autowired
    public AdRecommendStrategyController(AdRecommendStrategyService adRecommendStrategyService) {
        super(adRecommendStrategyService);
        this.adRecommendStrategyService=adRecommendStrategyService;
    }

}

