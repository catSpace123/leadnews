package com.feng.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.article.pojo.ApArticle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 文章信息表，存储已发布的文章 Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     * 根据条件查询文章
     * @param page
     * @param size
     * @param channelId
     * @return
     */
    List<ApArticle> searchArticle(@Param("page") Long page, @Param("size") Long size, @Param("channelId") Integer channelId);

    /**
     * 查询总记录数
     * @param channelId 频道id
     * @return
     */
    long count(@Param("channelId") Integer channelId);
}
