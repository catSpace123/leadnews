package com.feng.dfs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * dfs feign远程调用
 */
@FeignClient(value = "leadnews-dfs",path = "/dfs")
public interface DfsFeign {

    /**
     * 下载图片，获取图片字节数组
     * @param imgList
     * @return
     */
    @PostMapping("/downloadFile")
    public List<byte[]> getImageByte(@RequestBody List<String> imgList);

}
