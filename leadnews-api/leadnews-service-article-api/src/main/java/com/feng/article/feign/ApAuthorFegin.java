package com.feng.article.feign;

import com.feng.article.pojo.ApAuthor;
import com.feng.common.pojo.Result;
import com.feng.core.feign.CoreFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 文章fegin接口
 */
@FeignClient(value = "leadnews-article",path = "/apAuthor",contextId = "ApAuthor")
public interface ApAuthorFegin extends CoreFeign<ApAuthor> {

    /**
     * 创建作者
     */
    @PostMapping("/saveAuthor")
    public Result saveAuthor(@RequestBody ApAuthor ApAuthor);


    @GetMapping("wmUserId/{id}")
    public Result<ApAuthor> findByWmUserId(@PathVariable("id") Integer wmUserId);
}
