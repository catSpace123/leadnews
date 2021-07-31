package com.feng.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.article.pojo.ApArticle;
import com.feng.article.pojo.ArticleBehaviourDtoQuery;
import com.feng.article.pojo.ArticleInfoDto;
import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.PageRequestDto;

import java.util.Map;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface ApArticleService extends IService<ApArticle> {
    /**
     * 保存文章，信息内容等等
     * @param articleInfoDto
     * @return
     */
    ApArticle saveApArticle(ArticleInfoDto articleInfoDto);

    /**
     * 查询文章列表
     * @param pageRequestDto 接收分页对象
     * @return
     */
    PageInfo<ApArticle> searchArticle(PageRequestDto<ApArticle> pageRequestDto);


    /**
     * 查询该用户是否对文章点赞评论收藏
     * @param articleBehaviourDtoQuery
     * @return
     */
    Map<String, Object> ArticleBehaviourDtoQuery(ArticleBehaviourDtoQuery articleBehaviourDtoQuery);

}
