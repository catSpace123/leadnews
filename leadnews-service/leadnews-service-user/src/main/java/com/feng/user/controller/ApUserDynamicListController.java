package com.feng.user.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUserDynamicList;
import com.feng.user.service.ApUserDynamicListService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户动态列表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP用户动态列表",tags = "ApUserDynamicListController")
@RestController
@RequestMapping("/apUserDynamicList")
public class ApUserDynamicListController extends AbstractCoreController<ApUserDynamicList> {

    private ApUserDynamicListService apUserDynamicListService;

    //注入
    @Autowired
    public ApUserDynamicListController(ApUserDynamicListService apUserDynamicListService) {
        super(apUserDynamicListService);
        this.apUserDynamicListService=apUserDynamicListService;
    }

}

