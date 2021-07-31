package com.feng.article.controller;


import com.feng.article.pojo.ApArticle;
import com.feng.article.pojo.ArticleBehaviourDtoQuery;
import com.feng.article.pojo.ArticleInfoDto;
import com.feng.article.service.ApArticleService;
import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.PageRequestDto;
import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
* <p>
* 文章信息表，存储已发布的文章 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="文章信息表，存储已发布的文章",tags = "ApArticleController")
@RestController
@RequestMapping("/apArticle")
public class ApArticleController extends AbstractCoreController<ApArticle> {

    private ApArticleService apArticleService;

    /**
     * 注入
     * @param apArticleService
     */
    @Autowired
    public ApArticleController(ApArticleService apArticleService) {
        super(apArticleService);
        this.apArticleService=apArticleService;
    }

    /**
     * 保存文章，信息内容等等
     * @param articleInfoDto
     * @return
     */
    @PostMapping("/saveApArticle")
    public Result<ApArticle> saveApArticle(@RequestBody ArticleInfoDto articleInfoDto){

        ApArticle apArticle = apArticleService.saveApArticle(articleInfoDto);

        return Result.ok(apArticle);
    }


    /**
     * 查询文章列表
     * @param pageRequestDto 接收分页对象
     * @return
     */
    @PostMapping("/searchArticle")
    public Result<PageInfo<ApArticle>> searchArticle(@RequestBody PageRequestDto<ApArticle> pageRequestDto){

        PageInfo<ApArticle> pageInfoResult =  apArticleService.searchArticle(pageRequestDto);

       return Result.ok(pageInfoResult);
    }


    /**
     * 查询该用户是否对文章点赞评论收藏
     * @param articleBehaviourDtoQuery
     * @return
     */
    @PostMapping("/behaviourDtoQuery")
    public Result<Map<String,Object>> ArticleBehaviourDtoQuery(@RequestBody ArticleBehaviourDtoQuery articleBehaviourDtoQuery){

        Map<String,Object> map =  apArticleService.ArticleBehaviourDtoQuery(articleBehaviourDtoQuery);

       return Result.ok(map);

    }

}

