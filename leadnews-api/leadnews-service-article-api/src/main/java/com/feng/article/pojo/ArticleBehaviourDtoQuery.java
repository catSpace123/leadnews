package com.feng.article.pojo;

import lombok.Data;

@Data
public class ArticleBehaviourDtoQuery {
    /**
     * 设备ID
     */
    Integer equipmentId;
    /**
     * 文章ID
     */
    Long articleId;
    /**
     * 作者ID
     */
    Integer authorId;
}