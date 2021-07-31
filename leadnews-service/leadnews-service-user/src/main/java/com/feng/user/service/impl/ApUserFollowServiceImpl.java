package com.feng.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.article.feign.ApAuthorFegin;
import com.feng.article.pojo.ApAuthor;
import com.feng.behaviour.dto.FollowBehaviorDto;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.ulits.BusinessConstants;
import com.feng.user.mapper.ApUserFanMapper;
import com.feng.user.mapper.ApUserFollowMapper;
import com.feng.user.mapper.ApUserMapper;
import com.feng.user.pojo.ApUser;
import com.feng.user.pojo.ApUserFan;
import com.feng.user.pojo.ApUserFollow;
import com.feng.user.pojo.dto.UserRelationDto;
import com.feng.user.service.ApUserFollowService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * APP用户关注信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2021-07-09
 */
@Service
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {

    @Autowired
    private ApUserFollowMapper apUserFollowMapper;

    @Autowired
    private ApUserFanMapper apUserFanMapper;
    @Autowired
    private ApAuthorFegin apAuthorFegin;

    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;
    /**
     * 关注和取消关注
     * @param userRelationDto
     * @param userId  当前用户id
     */
    @Override
    public void likeOrUnLike(UserRelationDto userRelationDto, Integer userId) {
        //文章id
        Long articleId = userRelationDto.getArticleId();
        //作者id
        Integer authorId = userRelationDto.getAuthorId();
        //作者名称
        String authorName = userRelationDto.getAuthorName();
        //类型。关注还是取消关注
        Integer operation = userRelationDto.getOperation();

        //如果等于1表示关注

            //根据作者id获取作者对应的appuserid
            ApAuthor apAuthor = apAuthorFegin.findById(authorId).getData();
            if (apAuthor == null) {
                throw new LeadnewsException("没有该作者");
            }
        if(operation == 1) {
            //向userfollow 表添加关注记录
            //先查询是否存在关注记录，有记录则不需要再次关注
            QueryWrapper<ApUserFollow> querywrapper = new QueryWrapper<>();
            querywrapper.eq("user_id", userId)
                    //被关注的用户id
                    .eq("follow_id", apAuthor.getUserId());
            ApUserFollow apUserFollow = apUserFollowMapper.selectOne(querywrapper);
            if (apUserFollow != null) {
                throw new LeadnewsException("已经关注");
            }

            apUserFollow = new ApUserFollow();
            apUserFollow.setUserId(userId);
            apUserFollow.setFollowId(apAuthor.getUserId());
            apUserFollow.setFollowName(authorName);
            apUserFollow.setLevel(0);
            apUserFollow.setIsNotice(1);
            apUserFollow.setCreatedTime(LocalDateTime.now());
            //添加记录
            apUserFollowMapper.insert(apUserFollow);

            //先查询粉丝表有没有记录
            QueryWrapper<ApUserFan> querywrepper1 = new QueryWrapper<>();
            querywrepper1.eq("user_id", apAuthor.getUserId());
            querywrepper1.eq("fans_id", userId);
            ApUserFan apUserFan = apUserFanMapper.selectOne(querywrepper1);

            if (apUserFan != null) {
                throw new LeadnewsException("已经关注");
            }
            apUserFan = new ApUserFan();
            apUserFan.setUserId(apAuthor.getUserId());
            apUserFan.setFansId(userId);
            //根据userID查询用户名称
            ApUser apUser = apUserMapper.selectById(userId);
            if(apUser == null){
                throw new LeadnewsException("关注的作者已经不存在");
            }
            apUserFan.setFansName(apUser.getName());
            //粉丝忠实度 0 表示正常   1 潜力股 2 勇士
            apUserFan.setLevel(0);
            //创建时间
            apUserFan.setCreatedTime(LocalDateTime.now());
            //是否可见我动态
            apUserFan.setIsDisplay(1);
            //是否屏蔽私信 0 表示不屏蔽
            apUserFan.setIsShieldLetter(0);
            //是否屏蔽评论
            apUserFan.setIsShieldComment(0);

            //保存粉丝记录
            apUserFanMapper.insert(apUserFan);
            FollowBehaviorDto dto = new FollowBehaviorDto();
            dto.setArticleId(articleId);
            dto.setUserId(userId);
            dto.setFollowId(apAuthor.getUserId());


            //发送消息到行为微服务
            kafkaTemplate.send(BusinessConstants.ContentAudit.AP_USER_BEHAVIOUR_OR_UP_TOPIC, JSON.toJSONString(dto));
        }else{
            //取消关注  分别删除俩张表的记录
            QueryWrapper<ApUserFollow> querywrapper = new QueryWrapper<>();
            querywrapper.eq("user_id", userId)
                    //被关注的用户id
                    .eq("follow_id", apAuthor.getUserId());
            apUserFollowMapper.delete(querywrapper);


            QueryWrapper<ApUserFan> querywrapper1 = new QueryWrapper<>();
            querywrapper1.eq("user_id", apAuthor.getUserId());
            querywrapper1.eq("fans_id", userId);
            apUserFanMapper.delete(querywrapper1);
        }

    }


    /**
     * 根据当前用户id和朋友id查询是否关注
     * @param userId
     * @param currentUserId
     * @return
     */
    @Override
    public ApUserFollow findLike(Integer userId, Long currentUserId) {

        QueryWrapper<ApUserFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",currentUserId);
        queryWrapper.eq("follow_id",userId);
        ApUserFollow apUserFollow = apUserFollowMapper.selectOne(queryWrapper);

        return apUserFollow;
    }
}
