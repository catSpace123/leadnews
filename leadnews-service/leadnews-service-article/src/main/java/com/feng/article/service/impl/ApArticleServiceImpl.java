package com.feng.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.article.mapper.ApArticleConfigMapper;
import com.feng.article.mapper.ApArticleContentMapper;
import com.feng.article.mapper.ApArticleMapper;
import com.feng.article.mapper.ApCollectionMapper;
import com.feng.article.pojo.*;
import com.feng.article.service.ApArticleService;
import com.feng.behaviour.feign.ApBehaviorEntryFeign;
import com.feng.behaviour.feign.ApLikesBehaviorfeign;
import com.feng.behaviour.feign.ApUnlikesBehaviorfeign;
import com.feng.behaviour.pojo.ApBehaviorEntry;
import com.feng.behaviour.pojo.ApLikesBehavior;
import com.feng.behaviour.pojo.ApUnlikesBehavior;
import com.feng.common.exception.LeadnewsException;
import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.PageRequestDto;
import com.feng.common.pojo.Result;
import com.feng.common.ulits.GetUserIdHeaderUtil;
import com.feng.common.ulits.SystemConstants;
import com.feng.user.feign.ApUserFollowFeign;
import com.feng.user.pojo.ApUserFollow;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private ApBehaviorEntryFeign apBehaviorEntryFeign;

    @Autowired
    private ApLikesBehaviorfeign apLikesBehaviorfeign;

    @Autowired
    private ApUnlikesBehaviorfeign apUnlikesBehaviorfeign;
    @Autowired
    private ApCollectionMapper apCollectionMapper;

    @Autowired
    private ApUserFollowFeign apUserFollowFeign;


    /**
     * 保存文章，信息内容等等
     * @param articleInfoDto
     * @return
     */
    @Override
    public ApArticle saveApArticle(ArticleInfoDto articleInfoDto) {
        if(articleInfoDto == null) {
            throw new LeadnewsException("保存内容异常");
        }
            ApArticle apArticle = articleInfoDto.getApArticle();
            ApArticleConfig apArticleConfig = articleInfoDto.getApArticleConfig();
            ApArticleContent apArticleContent = articleInfoDto.getApArticleContent();
            //如果没有文章id就表示新增
            if (apArticle.getId() == null){
                //新增文章并返回id
                int insert = apArticleMapper.insert(apArticle);

                //设置文章id
                apArticleConfig.setArticleId(apArticle.getId());
                //保存文章配置
                apArticleConfigMapper.insert(apArticleConfig);
                //设置文章id
                apArticleContent.setArticleId(apArticle.getId());
                apArticleContentMapper.insert(apArticleContent);
            }else{
                //修改文章表
                apArticleMapper.updateById(apArticle);
                //修改文章配置表
                QueryWrapper<ApArticleConfig> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("article_id",apArticle.getId());
                apArticleConfigMapper.update(apArticleConfig,queryWrapper);

                //修改文章内容表
                QueryWrapper<ApArticleContent> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("article_id",apArticle.getId());
                apArticleContentMapper.update(apArticleContent,queryWrapper1);
            }

        return apArticle;
    }



    /**
     * 查询文章列表
     * @param pageRequestDto 接收分页对象
     * @return
     */
    @Override
    public PageInfo<ApArticle> searchArticle(PageRequestDto<ApArticle> pageRequestDto) {
        PageInfo<ApArticle> pageInfo = new PageInfo<>();
        //根据频道id查询没有没下架和删除的文章列表
        if(pageRequestDto == null ){
            return pageInfo;
        }
        Long page = pageRequestDto.getPage();
        Long size = pageRequestDto.getSize();
        Integer channelId = 0;
        if(pageRequestDto.getBody().getChannelId() != null){
            channelId = pageRequestDto.getBody().getChannelId();
        }
        List<ApArticle> apArticles = apArticleMapper.searchArticle(((page - 1) * size),size ,channelId);

        //查询总记录数
        long count = apArticleMapper.count(channelId);

        //计数共有多少页
        long totalPages = count / size +  (count % size > 0 ? 1 : 0);

        pageInfo.setPage(page);
        pageInfo.setSize(size);
        pageInfo.setTotal(count);
        pageInfo.setTotalPages(totalPages);
        pageInfo.setList(apArticles);
        return pageInfo;
    }


    /**
     * 查询该用户是否对文章点赞评论收藏
     * @param DtoQuery
     * @return
     */
    @Override
    public Map<String, Object> ArticleBehaviourDtoQuery(ArticleBehaviourDtoQuery DtoQuery) {

        Map<String, Object> map = new HashMap<>();

        //创建默认的返回值
        //1.定义变量
        //是否喜欢 默认是false
        boolean isunlike=false;
        //是否点赞 默认是false
        boolean islike = false;
        //是否收藏
        boolean isCollection = false;
        //是否关注
        boolean isFollow = false;

        map.put("isunlike",isunlike);
        map.put("islike",islike);
        map.put("isCollection",isCollection);
        map.put("isFollow",isFollow);


        ApBehaviorEntry data = null;
        //获取当前用户id
        String userId = GetUserIdHeaderUtil.getUserId();
        if("0".equals(userId)){
            //表示设备登录  根据id获取实体信息
            data = apBehaviorEntryFeign.findEntry(DtoQuery.getEquipmentId(), SystemConstants.JWT_FAIL).getData();
            if(data == null){
                return map;
            }
        }else{
            //是真实用户id查询
            data = apBehaviorEntryFeign.findEntry(Integer.valueOf(userId),SystemConstants.TYPE_USER).getData();
            if(data == null){
                return map;
            }
        }

        //实体id
        Integer id = data.getId();
        //根据实体id和文章id查询是否点赞
        ApLikesBehavior apLikesBehavior = apLikesBehaviorfeign.findLike(id, DtoQuery.getArticleId()).getData();
        if(apLikesBehavior != null){
            islike = true;
            map.put("islike",islike);
        }

        //是否喜欢
        ApUnlikesBehavior apUnlikesBehavior = apUnlikesBehaviorfeign.findLike(id, DtoQuery.getArticleId()).getData();
        if(apUnlikesBehavior != null){
            isunlike = true;
            map.put("isunlike",isunlike);
        }

        //是否收藏
        QueryWrapper<ApCollection> querywrapper = new QueryWrapper<>();
        querywrapper.eq("entry_id",id);
        querywrapper.eq("article_id",DtoQuery.getArticleId());
        ApCollection apCollection = apCollectionMapper.selectOne(querywrapper);
        if(apCollection != null){
            isCollection = true;
            map.put("isCollection",isCollection);
        }
        //是否关注
        ApUserFollow apUserFollow = apUserFollowFeign.findFollow(DtoQuery.getAuthorId(), Long.valueOf(userId)).getData();

        if(apUserFollow != null){
            isFollow = true;
            map.put("isFollow",isFollow);

        }
        return map;
    }
}
