package com.feng.article.controller;


import com.feng.article.pojo.ApArticleContent;
import com.feng.article.service.ApArticleContentService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP已发布文章内容表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP已发布文章内容表",tags = "ApArticleContentController")
@RestController
@RequestMapping("/apArticleContent")
public class ApArticleContentController extends AbstractCoreController<ApArticleContent> {

    private ApArticleContentService apArticleContentService;

    //注入
    @Autowired
    public ApArticleContentController(ApArticleContentService apArticleContentService) {
        super(apArticleContentService);
        this.apArticleContentService=apArticleContentService;
    }

}

