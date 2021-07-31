package com.feng.behaviour.feign;

import com.feng.behaviour.pojo.ApLikesBehavior;
import com.feng.behaviour.pojo.ApUnlikesBehavior;
import com.feng.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 喜欢表的feign接口
 */
@FeignClient(value = "leadnews-behaviour",path = "apUnlikesBehavior",contextId = "apUnlikesBehavior")
public interface ApUnlikesBehaviorfeign {


    /**
     * 根据实体id和文章id查询该用户是否对文章喜欢
     * @param entryId
     * @param articleId
     * @return
     */
    @GetMapping("/findLike")
    public Result<ApUnlikesBehavior> findLike(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId);

}
