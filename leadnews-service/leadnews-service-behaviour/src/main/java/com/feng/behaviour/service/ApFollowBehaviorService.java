package com.feng.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.behaviour.dto.FollowBehaviorDto;
import com.feng.behaviour.pojo.ApFollowBehavior;

/**
 * <p>
 * APP关注行为表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
public interface ApFollowBehaviorService extends IService<ApFollowBehavior> {

    /**
     * 关注行为记录
     * @param followBehaviorDto
     */
    void saveFollow(FollowBehaviorDto followBehaviorDto);
}
