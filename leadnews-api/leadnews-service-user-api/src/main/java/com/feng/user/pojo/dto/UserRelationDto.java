package com.feng.user.pojo.dto;

import lombok.Data;

/**
 *
     * @description:  用来接收前端的关注行为
     * @author: Cat
     * @date: 2021/7/19 20:27
     * @param: null
     * @return:
 */

@Data
public class UserRelationDto {

    /**
     * 文章作者ID
     */
    Integer authorId;

    /**
     * 作者名称
     */
    String authorName;

    /**
     * 文章id
     */
    Long articleId;
    /**
     * 操作方式
     * 1  关注
     * 0  取消
     */
    Integer operation;
}