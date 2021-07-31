package com.feng.user.feign;

import com.feng.common.pojo.Result;
import com.feng.user.pojo.ApUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * 用户信息feign接口
 */
@FeignClient(value = "leadnews-user",path = "/apUser",contextId = "apUser")
public interface ApUserFeign {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ApUser> findById(@PathVariable(name = "id") Serializable id);
}
