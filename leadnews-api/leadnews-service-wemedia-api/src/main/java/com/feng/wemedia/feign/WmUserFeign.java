package com.feng.wemedia.feign;

import com.feng.common.pojo.Result;
import com.feng.core.feign.CoreFeign;
import com.feng.wemedia.pojo.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 自媒体微服务接口
 */
@FeignClient(value = "leadnews-wemedia",path = "/wmUser",contextId = "WmUser")
public interface WmUserFeign extends CoreFeign<WmUser> {    //继承通用的fegin 有通用的增删改查

    /**
     * 创建自媒体账号
     */
    @PostMapping("/saveWmUser")
    public Result<WmUser> saveWmUser(@RequestBody WmUser wmUser);
}
