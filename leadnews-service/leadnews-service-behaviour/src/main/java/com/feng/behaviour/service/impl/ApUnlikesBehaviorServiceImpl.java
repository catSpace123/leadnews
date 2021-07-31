package com.feng.behaviour.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.behaviour.mapper.ApUnlikesBehaviorMapper;
import com.feng.behaviour.pojo.ApUnlikesBehavior;
import com.feng.behaviour.service.ApUnlikesBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP不喜欢行为表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
@Service
public class ApUnlikesBehaviorServiceImpl extends ServiceImpl<ApUnlikesBehaviorMapper, ApUnlikesBehavior> implements ApUnlikesBehaviorService {

    @Autowired
    private ApUnlikesBehaviorMapper apUnlikesBehaviorMapper;
    /**
     * 根据实体id和文章id查询该用户是否对文章喜欢
     * @param entryId  实体id
     * @param articleId 文章id
     * @return
     */
    @Override
    public ApUnlikesBehavior findUnLike(Integer entryId, Long articleId) {

        /**
         * 根据实体id和文章id查询该用户是否对文章喜欢
         */
        QueryWrapper<ApUnlikesBehavior> querywrapper = new QueryWrapper<>();
        querywrapper.eq("entry_id",entryId);
        querywrapper.eq("article_id",articleId);
        ApUnlikesBehavior apUnlikesBehavior = apUnlikesBehaviorMapper.selectOne(querywrapper);
        return apUnlikesBehavior;
    }
}
