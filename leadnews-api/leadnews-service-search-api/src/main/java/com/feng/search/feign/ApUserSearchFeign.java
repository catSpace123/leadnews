package com.feng.search.feign;

import com.feng.common.pojo.Result;
import com.feng.search.document.ArticleInfoDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 搜索自媒体feign接口
 */
@FeignClient(value = "leadnews-search",path = "/apUserSearch",contextId = "apUserSearch")
public interface ApUserSearchFeign {

    @PostMapping("/saveEs")
    public Result saveEs(@RequestBody ArticleInfoDocument articleInfoDocument);

}
