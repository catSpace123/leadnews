package com.feng.wemedia.controller;


import com.feng.common.pojo.Result;
import com.feng.common.ulits.GetUserIdHeaderUtil;
import com.feng.core.controller.AbstractCoreController;
import com.feng.wemedia.pojo.WmMaterial;
import com.feng.wemedia.service.WmMaterialService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

/**
* <p>
* 自媒体图文素材信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="自媒体图文素材信息表",tags = "WmMaterialController")
@RestController
@RequestMapping("/wmMaterial")
public class WmMaterialController extends AbstractCoreController<WmMaterial> {

    private WmMaterialService wmMaterialService;

    //注入
    @Autowired
    public WmMaterialController(WmMaterialService wmMaterialService) {
        super(wmMaterialService);
        this.wmMaterialService=wmMaterialService;
    }

    /**
     * 添加素材，保存图片uri
     * @param record
     * @return
     */
    @Override
    @PostMapping
    public Result<WmMaterial> insert(@RequestBody WmMaterial record) {
        /*接收上游的请求头，用户id
         */
        String userId = GetUserIdHeaderUtil.getUserId();
        /**
         * 封装信息保存素材
         */
        record.setUserId(Integer.valueOf(userId));
        record.setCreatedTime(LocalDateTime.now());
        record.setType(0);
        record.setIsCollection(0);
        wmMaterialService.save(record);
        return Result.ok();
    }


    /**
     * 查询自媒体素材
     */

    @Override
    @GetMapping("/findMaterial")
    public Result<List<WmMaterial>> findByRecord(WmMaterial record) {
        //获取用户id
        String userId = GetUserIdHeaderUtil.getUserId();
        record.setUserId(Integer.valueOf(userId));
        //根据自媒体id查询素材
        return super.findByRecord(record);
    }
}

