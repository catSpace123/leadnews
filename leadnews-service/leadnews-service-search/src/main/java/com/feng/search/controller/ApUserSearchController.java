package com.feng.search.controller;


import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import com.feng.search.document.ArticleInfoDocument;
import com.feng.search.pojo.ApUserSearch;
import com.feng.search.service.ApUserSearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户搜索信息表 控制器</p>
* @author ljh
* @since 2021-07-22
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

    /**
     * 用来后台审核通过后将自媒体的文章保存到es中
     * @param articleInfoDocument
     * @return
     */
    @PostMapping("/saveEs")
    public Result saveEs(@RequestBody ArticleInfoDocument articleInfoDocument){
        apUserSearchService.saveEs(articleInfoDocument);
        return Result.ok();
    }

}

