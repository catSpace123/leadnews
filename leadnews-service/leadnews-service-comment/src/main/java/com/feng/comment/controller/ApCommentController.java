package com.feng.comment.controller;


import com.feng.comment.dto.CommentSaveDto;
import com.feng.comment.pojo.ApComment;
import com.feng.comment.service.ApCommentService;
import com.feng.common.pojo.Result;
import com.feng.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP评论信息表 控制器</p>
* @author ljh
* @since 2021-07-21
*/
@Api(value="APP评论信息表",tags = "ApCommentController")
@RestController
@RequestMapping("/apComment")
public class ApCommentController extends AbstractCoreController<ApComment> {

    private ApCommentService apCommentService;

    //注入
    @Autowired
    public ApCommentController(ApCommentService apCommentService) {
        super(apCommentService);
        this.apCommentService=apCommentService;
    }


    /**
     * 发表评论
     * @param commentSaveDto  接收实体
     * @return
     */
    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody CommentSaveDto commentSaveDto){

        apCommentService.saveComment(commentSaveDto);
        return Result.ok();
    }
}

