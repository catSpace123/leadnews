package com.feng.behaviour.feign;

import com.feng.behaviour.pojo.ApLikesBehavior;
import com.feng.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 点赞feign接口
 */
@FeignClient(value = "leadnews-behaviour",path = "/apLikesBehavior",contextId = "apLikesBehavior")
public interface ApLikesBehaviorfeign {



    /**
     * 根据实体id和文章id查询该用户是否对文章点赞
     * @param entryId
     * @param articleId
     * @return
     */
    @GetMapping("/findLike")
    public Result<ApLikesBehavior> findLike(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId);

}
