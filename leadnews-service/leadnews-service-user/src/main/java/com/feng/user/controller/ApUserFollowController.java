package com.feng.user.controller;


import com.feng.behaviour.pojo.ApUnlikesBehavior;
import com.feng.common.pojo.Result;
import com.feng.common.pojo.StatusCode;
import com.feng.common.ulits.GetUserIdHeaderUtil;
import com.feng.core.controller.AbstractCoreController;
import com.feng.user.pojo.ApUserFollow;
import com.feng.user.pojo.dto.UserRelationDto;
import com.feng.user.service.ApUserFollowService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* <p>
* APP用户关注信息表 控制器</p>
* @author ljh
* @since 2021-07-09
*/
@Api(value="APP用户关注信息表",tags = "ApUserFollowController")
@RestController
@RequestMapping("/apUserFollow")
public class ApUserFollowController extends AbstractCoreController<ApUserFollow> {

    private ApUserFollowService apUserFollowService;

    //注入
    @Autowired
    public ApUserFollowController(ApUserFollowService apUserFollowService) {
        super(apUserFollowService);
        this.apUserFollowService=apUserFollowService;
    }

    /**
     * 关注和取消关注
     * @param userRelationDto
     * @return
     */
    @PostMapping("/likeOrUnLike")
    public Result likeOrUnLike(@RequestBody UserRelationDto userRelationDto){
        Integer userId = Integer.valueOf(GetUserIdHeaderUtil.getUserId());
        //不允许关注操作
        if(userRelationDto == null){
            return Result.error("需要登录在操作");
        }

        //如果为0 表示游客登录
        if (GetUserIdHeaderUtil.isAnonymous()){
            return Result.error(StatusCode.ROLE);
        }
        apUserFollowService.likeOrUnLike(userRelationDto,userId);
        return Result.ok();
    }

    /**
     * 根据当前用户id和朋友id查询是否关注
     * @param userId
     * @param currentUserId
     * @return
     */
    @GetMapping("/findFollow")
    public Result<ApUserFollow> findFollow(@RequestParam("userId") Integer userId, @RequestParam("currentUserId") Long currentUserId){


        ApUserFollow apUserFollow =  apUserFollowService.findLike(userId,currentUserId);
        return Result.ok(apUserFollow);
    }

}

