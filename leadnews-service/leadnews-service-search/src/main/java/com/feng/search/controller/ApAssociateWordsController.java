package com.feng.search.controller;


import com.feng.core.controller.AbstractCoreController;
import com.feng.search.pojo.ApAssociateWords;
import com.feng.search.service.ApAssociateWordsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 联想词表 控制器</p>
* @author ljh
* @since 2021-07-22
*/
@Api(value="联想词表",tags = "ApAssociateWordsController")
@RestController
@RequestMapping("/apAssociateWords")
public class ApAssociateWordsController extends AbstractCoreController<ApAssociateWords> {

    private ApAssociateWordsService apAssociateWordsService;

    //注入
    @Autowired
    public ApAssociateWordsController(ApAssociateWordsService apAssociateWordsService) {
        super(apAssociateWordsService);
        this.apAssociateWordsService=apAssociateWordsService;
    }

}

