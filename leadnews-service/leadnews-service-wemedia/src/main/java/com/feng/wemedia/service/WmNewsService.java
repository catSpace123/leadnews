package com.feng.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.PageRequestDto;
import com.feng.wemedia.pojo.*;

import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface WmNewsService extends IService<WmNews> {


    /**
     * 查询自媒体文章列表
     * @param pageRequestDto
     * @return
     */
    PageInfo<WmNewsVo> findWmNewsList(PageRequestDto<WmNewsDto> pageRequestDto);


    /**
     * 提交保存自媒体文章
     * @return
     */
    void saveWmNew(WmNewsDtoSave wmNewsDtoSave);


    /**
     *
     * 根据id获取文章
     */
    WmNewsDtoSave getWmNewsById(int id);


    /**
     * 用于查询未审核通过的和人工审核列表，进行人工审核
     * @param title  查询条件
     * @return
     */
    PageInfo<WmNewsSearchVo> findWmNewsAudit(Long page, Long pagesize, String title);
}
