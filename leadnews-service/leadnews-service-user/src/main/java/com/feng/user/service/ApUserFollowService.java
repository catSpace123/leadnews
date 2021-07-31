package com.feng.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.user.pojo.ApUserFollow;
import com.feng.user.pojo.dto.UserRelationDto;

/**
 * <p>
 * APP用户关注信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface ApUserFollowService extends IService<ApUserFollow> {


    /**
     * 关注和取消关注
     * @param userRelationDto
     * @param userId
     */
    void likeOrUnLike(UserRelationDto userRelationDto, Integer userId);


    /**
     * 根据当前用户id和朋友id查询是否关注
     * @param userId
     * @param currentUserId
     * @return
     */
    ApUserFollow findLike(Integer userId, Long currentUserId);
}
