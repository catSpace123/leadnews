package com.feng.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.behaviour.pojo.ApLikesBehavior;

/**
 * <p>
 * APP点赞行为表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
public interface ApLikesBehaviorService extends IService<ApLikesBehavior> {



    /**
     * 根据实体id和文章id查询该用户是否对文章点赞
     * @param entryId
     * @param articleId
     * @return
     */
    ApLikesBehavior findLike(Integer entryId, Long articleId);
}
