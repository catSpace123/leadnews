package com.feng.wemedia.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.wemedia.pojo.WmNewsStatistics;
import com.feng.wemedia.service.WmNewsStatisticsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体图文数据统计表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="自媒体图文数据统计表",tags = "WmNewsStatisticsController")
@RestController
@RequestMapping("/wmNewsStatistics")
public class WmNewsStatisticsController extends AbstractCoreController<WmNewsStatistics> {

    private WmNewsStatisticsService wmNewsStatisticsService;

    //注入
    @Autowired
    public WmNewsStatisticsController(WmNewsStatisticsService wmNewsStatisticsService) {
        super(wmNewsStatisticsService);
        this.wmNewsStatisticsService=wmNewsStatisticsService;
    }

}

