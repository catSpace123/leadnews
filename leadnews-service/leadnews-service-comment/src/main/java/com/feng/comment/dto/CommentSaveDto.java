package com.feng.comment.dto;

import lombok.Data;

/**
 * 发表动态
 */
@Data
public class CommentSaveDto {
    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;
}