package com.feng.behaviour.dto;

import lombok.Data;

/**
 * 发送消息到行为微服务
 */
@Data
public class FollowBehaviorDto {
    /**
     * 文章id
     */
    Long articleId;
    /**
     * 被关注者 用户ID
     */
    Integer followId;
    /**
     * 关注者  用户id
     */
    Integer userId;
    /**
     * 设备ID
     */
    Integer equipmentId;
}