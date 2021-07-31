package com.feng.user.controller;


import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUserRealname;
import com.feng.user.service.ApUserRealnameService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
* <p>
* APP实名认证信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP实名认证信息表",tags = "ApUserRealnameController")
@RestController
@RequestMapping("/apUserRealname")
public class ApUserRealnameController extends AbstractCoreController<ApUserRealname> {

    private ApUserRealnameService apUserRealnameService;

    //注入
    @Autowired
    public ApUserRealnameController(ApUserRealnameService apUserRealnameService) {
        super(apUserRealnameService);
        this.apUserRealnameService=apUserRealnameService;
    }

    /**
     * 用来审核通过实名认证
     * @param id
     * @return
     */
    @PutMapping("/path/{id}")
    public Result realname(@PathVariable("id") int id){

        apUserRealnameService.realname(id);

       return Result.ok();
    }


    /**
     * 审核驳回
     * @param id
     * @param reason  驳回原因
     * @return
     */
    @PutMapping("/reject/{id}")
    public Result reject(@PathVariable("id") int id, @RequestParam String reason){

        apUserRealnameService.reject(id,reason);
        return Result.ok();

    }
}

