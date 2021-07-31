package com.feng.article.feign;

import com.feng.article.pojo.ApArticle;
import com.feng.article.pojo.ArticleInfoDto;
import com.feng.common.pojo.Result;
import com.feng.core.feign.CoreFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 文章的远程调用
 */
@FeignClient(value = "leadnews-article",path ="/apArticle",contextId = "ApArticle")
public interface ApArticleFeign extends CoreFeign<ApArticle> {


    /**
     * 保存文章，信息内容等等
     * @param articleInfoDto
     * @return
     */
    @PostMapping("/saveApArticle")
    public Result<ApArticle> saveApArticle(@RequestBody ArticleInfoDto articleInfoDto);
}
