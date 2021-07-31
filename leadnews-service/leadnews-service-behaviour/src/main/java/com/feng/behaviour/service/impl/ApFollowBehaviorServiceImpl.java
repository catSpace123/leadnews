package com.feng.behaviour.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.behaviour.dto.FollowBehaviorDto;
import com.feng.behaviour.mapper.ApFollowBehaviorMapper;
import com.feng.behaviour.pojo.ApBehaviorEntry;
import com.feng.behaviour.pojo.ApFollowBehavior;
import com.feng.behaviour.service.ApFollowBehaviorService;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.ulits.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * APP关注行为表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
@Service
public class ApFollowBehaviorServiceImpl extends ServiceImpl<ApFollowBehaviorMapper, ApFollowBehavior> implements ApFollowBehaviorService {

    @Autowired
    private ApBehaviorEntryServiceImpl apBehaviorEntryService;

    @Autowired
    private ApFollowBehaviorMapper apFollowBehaviorMapper;

    /**
     * 关注行为记录
     * @param followBehaviorDto
     */
    @Override
    public void saveFollow(FollowBehaviorDto followBehaviorDto) {

        Long articleId = followBehaviorDto.getArticleId();
        Integer equipmentId = followBehaviorDto.getEquipmentId();
        Integer followId = followBehaviorDto.getFollowId();
        Integer userId = followBehaviorDto.getUserId();
        //根据userid查询出实体id
        QueryWrapper<ApBehaviorEntry> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entry_id",userId);
        //当类型是用户的时候
        queryWrapper.eq("type", SystemConstants.TYPE_USER);
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryService.getOne(queryWrapper);
        if (apBehaviorEntry == null){
            throw new LeadnewsException("没有这个实体");
        }
        //向这个表添加记录
        ApFollowBehavior entity = new ApFollowBehavior();
        entity.setArticleId(articleId);
        entity.setEntryId(apBehaviorEntry.getId());
        entity.setFollowId(followId);
        entity.setCreatedTime(LocalDateTime.now());
        apFollowBehaviorMapper.insert(entity);

    }
}
