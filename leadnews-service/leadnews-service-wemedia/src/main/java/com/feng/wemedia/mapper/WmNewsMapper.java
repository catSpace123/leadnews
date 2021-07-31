package com.feng.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.wemedia.pojo.WmNews;
import com.feng.wemedia.pojo.WmNewsSearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-07-09
 */
public interface WmNewsMapper extends BaseMapper<WmNews> {
    /**
     *  select new.* ,wm_user.`name` from wm_news new LEFT JOIN wm_user on new.user_id = wm_user.id WHERE new.`status` in (2,3)
     *  and new.title like '%马%' LIMIT 2,2
     * @param page  页码
     * @param pagesize  每页显示条数
     * @param title  模糊查询条件
     * @return
     * 根据条件查询为审核通过的和人工审核的文章
     */
    List<WmNewsSearchVo> findWmNewsAudit(@Param("page") Long page, @Param("pagesize") Long pagesize, @Param("title")String title);

    /**
     * 查询总记录数
     * @param title
     * @return
     */
    long count(@Param("title") String title);
}
