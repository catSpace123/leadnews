package com.feng.behaviour.feign;

import com.feng.behaviour.pojo.ApBehaviorEntry;
import com.feng.behaviour.pojo.ApLikesBehavior;
import com.feng.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 查询实体信息
 */
@FeignClient(value = "leadnews-behaviour",path = "apBehaviorEntry",contextId = "apBehaviorEntry")
public interface ApBehaviorEntryFeign {


    /**
     * 查询实体信息
     * @param type
     * @param equipmentId
     * @return
     */
    @GetMapping("/findEntry")
    public Result<ApBehaviorEntry> findEntry(@RequestParam("type")Integer type, @RequestParam("equipmentId") Integer equipmentId);





    }
