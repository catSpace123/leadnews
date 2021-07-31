package com.feng.behaviour.controller;


import com.feng.behaviour.pojo.ApUnlikesBehavior;
import com.feng.behaviour.service.ApUnlikesBehaviorService;
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
* APP不喜欢行为表 控制器</p>
* @author ljh
* @since 2021-07-20
*/
@Api(value="APP不喜欢行为表",tags = "ApUnlikesBehaviorController")
@RestController
@RequestMapping("/apUnlikesBehavior")
public class ApUnlikesBehaviorController extends AbstractCoreController<ApUnlikesBehavior> {

    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    /**
     * 注入
     * @param apUnlikesBehaviorService
     */
    @Autowired
    public ApUnlikesBehaviorController(ApUnlikesBehaviorService apUnlikesBehaviorService) {
        super(apUnlikesBehaviorService);
        this.apUnlikesBehaviorService=apUnlikesBehaviorService;
    }


    /**
     * 根据实体id和文章id查询该用户是否对文章喜欢
     * @param entryId  实体id
     * @param articleId 文章id
     * @return
     */
    @GetMapping("/findUnLike")
    public Result<ApUnlikesBehavior> findUnLike(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId){

        ApUnlikesBehavior apUnlikesBehavior = apUnlikesBehaviorService.findUnLike(entryId,articleId);

        return Result.ok(apUnlikesBehavior);
    }


}

