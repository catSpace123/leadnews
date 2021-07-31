package com.feng.behaviour.controller;


import com.feng.behaviour.pojo.ApBehaviorEntry;
import com.feng.behaviour.service.ApBehaviorEntryService;
import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP行为实体表,一个行为实体可能是用户或者设备，或者其它 控制器</p>
* @author ljh
* @since 2021-07-20
*/
@Api(value="APP行为实体表,一个行为实体可能是用户或者设备，或者其它",tags = "ApBehaviorEntryController")
@RestController
@RequestMapping("/apBehaviorEntry")
public class ApBehaviorEntryController extends AbstractCoreController<ApBehaviorEntry> {

    private ApBehaviorEntryService apBehaviorEntryService;

    //注入
    @Autowired
    public ApBehaviorEntryController(ApBehaviorEntryService apBehaviorEntryService) {
        super(apBehaviorEntryService);
        this.apBehaviorEntryService=apBehaviorEntryService;
    }

    /**
     * 根据设备id获取实体
     *
     * @param equipmentId
     * @return
     */
    @GetMapping("/findEntry")
    public Result<ApBehaviorEntry> findEntry(@RequestParam("type")Integer type,@RequestParam("equipmentId") Integer equipmentId){


        ApBehaviorEntry entry = apBehaviorEntryService.findEntry(type,equipmentId);
        return Result.ok(entry);
    }
}

