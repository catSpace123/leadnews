package com.feng.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.comment.dto.CommentSaveDto;
import com.feng.comment.mapper.ApCommentMapper;
import com.feng.comment.mong.ApCommentDocument;
import com.feng.comment.pojo.ApComment;
import com.feng.comment.service.ApCommentService;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.ulits.GetUserIdHeaderUtil;
import com.feng.user.feign.ApUserFeign;
import com.feng.user.pojo.ApUser;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * <p>
 * APP评论信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2021-07-21
 */
@Service
public class ApCommentServiceImpl extends ServiceImpl<ApCommentMapper, ApComment> implements ApCommentService {

        @Autowired
        private MongoTemplate mongoTemplate;

        @Autowired
        private ApUserFeign apUserFeign;
    /**
     * 发表评论
     * @param Dto  接收实体
     * @return
     */

    @Override
    public void saveComment(CommentSaveDto Dto) {
         if(GetUserIdHeaderUtil.isAnonymous()){
             //游客不能进行评论
             throw new LeadnewsException("请登录");
         }

         if(StringUtils.isEmpty(Dto.getContent())){
             throw new LeadnewsException("[评论内容不能为空]");
         }
        Integer userId = Integer.valueOf(GetUserIdHeaderUtil.getUserId());

        ApCommentDocument apCommentDocument = new ApCommentDocument();

        //文章id
        apCommentDocument.setArticleId(Dto.getArticleId());
        //评论内容
        apCommentDocument.setContent(Dto.getContent());
        //评论人id
        apCommentDocument.setUserId(userId);

        //根据用户id查询用户的头像和昵称
        ApUser apUser = apUserFeign.findById(userId).getData();


        //评论人头像
        apCommentDocument.setHeadImage(apUser.getImage());
        //昵称
        apCommentDocument.setNickName(apUser.getName());
        //创建时间
        apCommentDocument.setCreatedTime(LocalDateTime.now());
        //修改时间
        apCommentDocument.setUpdatedTime(LocalDateTime.now());

        //总的点赞数
        apCommentDocument.setLikes(0);

        //总的回复数
        apCommentDocument.setReplys(0);
        mongoTemplate.insert(apCommentDocument);
    }
}
