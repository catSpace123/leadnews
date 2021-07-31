package com.feng.wemedia.controller;


import com.feng.common.pojo.Login;
import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import com.feng.wemedia.pojo.WmUser;
import com.feng.wemedia.service.WmUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
* <p>
* 自媒体用户信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="自媒体用户信息表",tags = "WmUserController")
@RestController
@RequestMapping("/wmUser")
public class WmUserController extends AbstractCoreController<WmUser> {

    private WmUserService wmUserService;

    //注入
    @Autowired
    public WmUserController(WmUserService wmUserService) {
        super(wmUserService);
        this.wmUserService=wmUserService;
    }

    /**
     * 用来啊添加自媒体账号
     * @param wmUser
     * @return
     */
    @PostMapping("/saveWmUser")
    public Result saveWmUser(@RequestBody WmUser wmUser){

        wmUserService.saveWmUser(wmUser);

        return Result.ok(wmUser);
    }



    /**
     * 自媒体用户登录
     * @return
     */
    @PostMapping("/login")
    public Result<Map<String,Object>> LoginMedia(@RequestBody Login login) {

        Map<String,Object> map =  wmUserService.LoginMedia(login);

        return Result.ok(map);
    }
}

