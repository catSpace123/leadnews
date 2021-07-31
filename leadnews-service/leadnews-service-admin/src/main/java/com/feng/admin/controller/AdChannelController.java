package com.feng.admin.controller;


import com.feng.admin.pojo.AdChannel;
import com.feng.admin.service.AdChannelService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 频道信息表 控制器</p>
* @author
* @since 2021-07-08
*/
@Api(value="频道信息表",tags = "AdChannelController")
@RestController
@RequestMapping("/adChannel")
public class AdChannelController extends AbstractCoreController<AdChannel> {

    private AdChannelService adChannelService;

    //注入
    @Autowired
    public AdChannelController(AdChannelService adChannelService) {
        super(adChannelService);
        this.adChannelService=adChannelService;
    }

}

