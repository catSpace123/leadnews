package com.feng.behaviour.controller;


import com.feng.behaviour.pojo.ApLikesBehavior;
import com.feng.behaviour.service.ApLikesBehaviorService;
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
* APP点赞行为表 控制器</p>
* @author ljh
* @since 2021-07-20
*/
@Api(value="APP点赞行为表",tags = "ApLikesBehaviorController")
@RestController
@RequestMapping("/apLikesBehavior")
public class ApLikesBehaviorController extends AbstractCoreController<ApLikesBehavior> {

    private ApLikesBehaviorService apLikesBehaviorService;

    //注入
    @Autowired
    public ApLikesBehaviorController(ApLikesBehaviorService apLikesBehaviorService) {
        super(apLikesBehaviorService);
        this.apLikesBehaviorService=apLikesBehaviorService;
    }

    /**
     * 根据实体id和文章id查询该用户是否对文章点赞
     * @param entryId
     * @param articleId
     * @return
     */
    @GetMapping("/findLike")
    public Result<ApLikesBehavior> findLike(@RequestParam("entryId") Integer entryId,@RequestParam("articleId") Long articleId){


        ApLikesBehavior apLikesBehavior =  apLikesBehaviorService.findLike(entryId,articleId);

        return Result.ok(apLikesBehavior);
    }
}

