package com.feng.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.comment.dto.CommentSaveDto;
import com.feng.comment.pojo.ApComment;

/**
 * <p>
 * APP评论信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-21
 */
public interface ApCommentService extends IService<ApComment> {


    /**
     * 发表评论
     * @param commentSaveDto  接收实体
     * @return
     */
    void saveComment(CommentSaveDto commentSaveDto);
}
