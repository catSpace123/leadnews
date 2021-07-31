package com.feng.behaviour.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.behaviour.mapper.ApLikesBehaviorMapper;
import com.feng.behaviour.pojo.ApLikesBehavior;
import com.feng.behaviour.service.ApLikesBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP点赞行为表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
@Service
public class ApLikesBehaviorServiceImpl extends ServiceImpl<ApLikesBehaviorMapper, ApLikesBehavior> implements ApLikesBehaviorService {


    @Autowired
    private ApLikesBehaviorMapper apLikesBehaviorMapper;
    /**
     * 根据实体id和文章id查询该用户是否对文章点赞
     * @param entryId
     * @param articleId
     * @return
     */
    @Override
    public ApLikesBehavior findLike(Integer entryId, Long articleId) {
        ApLikesBehavior apLikesBehavior = new ApLikesBehavior();

        if(entryId == null || articleId == 0){
            return apLikesBehavior;
        }

        //根据id查询
        QueryWrapper<ApLikesBehavior> querywrapper = new QueryWrapper<>();

        querywrapper.eq("entry_id",entryId)
                    .eq("article_id",articleId);
        apLikesBehavior = apLikesBehaviorMapper.selectOne(querywrapper);

        return apLikesBehavior;
    }
}
