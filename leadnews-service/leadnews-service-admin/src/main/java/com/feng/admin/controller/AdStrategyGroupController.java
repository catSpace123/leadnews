package com.feng.admin.controller;


import com.feng.admin.pojo.AdStrategyGroup;
import com.feng.admin.service.AdStrategyGroupService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 推荐策略分组信息表 控制器</p>
* @author ljh
* @since 2021-07-08
*/
@Api(value="推荐策略分组信息表",tags = "AdStrategyGroupController")
@RestController
@RequestMapping("/adStrategyGroup")
public class AdStrategyGroupController extends AbstractCoreController<AdStrategyGroup> {

    private AdStrategyGroupService adStrategyGroupService;

    //注入
    @Autowired
    public AdStrategyGroupController(AdStrategyGroupService adStrategyGroupService) {
        super(adStrategyGroupService);
        this.adStrategyGroupService=adStrategyGroupService;
    }

}

