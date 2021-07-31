package com.feng.user.feign;

import com.feng.common.pojo.Result;
import com.feng.user.pojo.ApUserFollow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "leadnews-user",path ="/apUserFollow" ,contextId = "apUserFollow")
public interface ApUserFollowFeign {

    /**
     * 根据当前用户id和朋友id查询是否关注
     * @param userId
     * @param currentUserId
     * @return
     */
    @GetMapping("/findFollow")
    public Result<ApUserFollow> findFollow(@RequestParam("userId") Integer userId, @RequestParam("currentUserId") Long currentUserId);


    }
