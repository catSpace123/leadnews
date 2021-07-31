package com.feng.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.behaviour.pojo.ApUnlikesBehavior;

/**
 * <p>
 * APP不喜欢行为表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
public interface ApUnlikesBehaviorService extends IService<ApUnlikesBehavior> {


    /**
     * 根据实体id和文章id查询该用户是否对文章喜欢
     * @param entryId  实体id
     * @param articleId 文章id
     * @return
     */
    ApUnlikesBehavior findUnLike(Integer entryId, Long articleId);
}
