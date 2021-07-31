package com.feng.wemedia.feign;

import com.feng.common.pojo.Result;
import com.feng.core.feign.CoreFeign;
import com.feng.wemedia.pojo.WmNews;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 自媒体文章feign
 */
@FeignClient(value = "leadnews-wemedia",path = "/wmNews",contextId = "WmNews")
public interface WmNewsFeign extends CoreFeign<WmNews> {

    /**
     * 查询自媒体文章表中的状态=8 的文章列表进行对比时间发布
     * @return
     */
    @GetMapping("/fundState")
    Result<List<WmNews>> fundState(@RequestParam("stare") int stare);
}
