package com.feng.article.controller;


import com.feng.article.pojo.ApEquipmentCode;
import com.feng.article.service.ApEquipmentCodeService;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP设备码信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP设备码信息表",tags = "ApEquipmentCodeController")
@RestController
@RequestMapping("/apEquipmentCode")
public class ApEquipmentCodeController extends AbstractCoreController<ApEquipmentCode> {

    private ApEquipmentCodeService apEquipmentCodeService;

    //注入
    @Autowired
    public ApEquipmentCodeController(ApEquipmentCodeService apEquipmentCodeService) {
        super(apEquipmentCodeService);
        this.apEquipmentCodeService=apEquipmentCodeService;
    }

}

