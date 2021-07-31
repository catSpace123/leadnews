package com.feng.user.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUserSearch;
import com.feng.user.service.ApUserSearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户搜索信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP用户搜索信息表",tags = "ApUserSearchController")
@RestController
@RequestMapping("/apUserSearch")
public class ApUserSearchController extends AbstractCoreController<ApUserSearch> {

    private ApUserSearchService apUserSearchService;

    //注入
    @Autowired
    public ApUserSearchController(ApUserSearchService apUserSearchService) {
        super(apUserSearchService);
        this.apUserSearchService=apUserSearchService;
    }

}
