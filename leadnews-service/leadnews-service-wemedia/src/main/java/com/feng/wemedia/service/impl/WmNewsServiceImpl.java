package com.feng.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.PageRequestDto;
import com.feng.common.ulits.BusinessConstants;
import com.feng.common.ulits.GetUserIdHeaderUtil;
import com.feng.wemedia.mapper.WmNewsMapper;
import com.feng.wemedia.mapper.WmUserMapper;
import com.feng.wemedia.pojo.*;
import com.feng.wemedia.service.WmNewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {
        @Autowired
       private WmNewsMapper wmNewsMapper;

        @Autowired
       private KafkaTemplate kafkaTemplate;

    /**
     * 查询自媒体文章列表
     * @param pageRequestDto
     * @return
     */
    @Override
    public PageInfo<WmNewsVo> findWmNewsList(PageRequestDto<WmNewsDto> pageRequestDto) {

        PageInfo<WmNewsVo> pageInfo = new PageInfo<>();

        List<WmNewsVo> list = new ArrayList<>();

        /**
         *根据条件查询自媒体用户的文章列表
         */
        Long currentPage = pageRequestDto.getPage();    //当前页码
        Long size = pageRequestDto.getSize();           //每页显示条数
        WmNewsDto wmNewsDto = pageRequestDto.getBody();

        Page<WmNews> page = new Page<>(currentPage,size);
        QueryWrapper<WmNews> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id",wmNewsDto.getUserId());   //当前用户id

        if(wmNewsDto != null){


        if(!StringUtils.isEmpty(wmNewsDto.getStatus())){
            queryWrapper.eq("status",wmNewsDto.getStatus());   //文章状态
        }

        if(!StringUtils.isEmpty(wmNewsDto.getTitle())){
            queryWrapper.like("title",wmNewsDto.getTitle());   //标题
        }

        if(!StringUtils.isEmpty(wmNewsDto.getChannelId())){
            queryWrapper.eq("channel_id",wmNewsDto.getChannelId()); //频道id
        }

        if(!StringUtils.isEmpty(wmNewsDto.getStartTime()) &&  !StringUtils.isEmpty(wmNewsDto.getEndTime()))
            queryWrapper.between("created_time",wmNewsDto.getStartTime(),wmNewsDto.getEndTime());   //时间范围查询
        }


        //查询
        IPage<WmNews> page1 = page(page, queryWrapper);
        List<WmNews> wmNews = page1.getRecords();

        if(wmNews != null){
            for (WmNews wmNew : wmNews) {
                WmNewsVo wmNewsVo = new WmNewsVo();

                BeanUtils.copyProperties(wmNew,wmNewsVo);
                //把字符串转化成数组，list
                wmNewsVo.setContent(JSON.parseArray(wmNew.getContent(),ContentVo.class));
                //把字符串转化成数组，在转成lisi
                wmNewsVo.setImages(Arrays.asList(wmNew.getImages().split(",")));
                list.add(wmNewsVo);
            }
        }

        pageInfo.setList(list);
        pageInfo.setPage(currentPage);
        pageInfo.setSize(size);
        pageInfo.setTotal(page1.getTotal());
        pageInfo.setTotalPages(page1.getPages());

        return pageInfo;
    }




    /**
     * 提交保存自媒体文章
     * @return
     */
    @Override
    public void saveWmNew(WmNewsDtoSave wmNewsDtoSave) {

        WmNews wmNews = new WmNews();
        /**
         * 获取用户id
         */
        String userId = GetUserIdHeaderUtil.getUserId();

        /**
         * 封装对象
         */
        BeanUtils.copyProperties(wmNewsDtoSave,wmNews);

        //
        wmNews.setUserId(Integer.valueOf(userId));
        //设置文本
        wmNews.setContent(JSON.toJSONString(wmNewsDtoSave.getContent()));

        /**
         * 如果为-1 就是自动，就需要在内容的图片里面获取个数来决定是单图还是多图
         */
        if(wmNewsDtoSave.getType() == -1){
            List<String> imageSize = getImageSize(wmNewsDtoSave);

            if(imageSize.size() == 0){
                wmNews.setType(0);
            }else if(imageSize.size() == 1){
                //把内容里面的图片设置成封面
                wmNews.setType(1);
                wmNews.setImages(imageSize.get(0));
            }else{
                wmNews.setType(3);
                wmNews.setImages(String.join(",",imageSize));
            }
        }

        /**
         * 判断封面图片是否为空  如果不为空，就表示自媒体用户自己上传了图片就用用户自己的封面
         */
        if(!StringUtils.isEmpty(wmNewsDtoSave.getImages())){
            /**
             * 把list转化成String
             */
            wmNews.setImages(String.join(",",wmNewsDtoSave.getImages()));
        }

        /**
         * 0 表示 保存草稿，1表示提交
         */
        if(wmNewsDtoSave.getStatus() != 0){
            //提交时间
            wmNews.setSubmitedTime(LocalDateTime.now());
        }
        wmNews.setEnable(0);
        /**
         * 判断id是否为空来决定是跟新还是添加
         */
        if(wmNewsDtoSave.getId() == null){
            //设置创建时间
            wmNews.setCreatedTime(LocalDateTime.now());
            wmNewsMapper .insert(wmNews);

        }else {
            //更新之后把理由置为空
            wmNews.setReason(null);
            wmNewsMapper.updateById(wmNews);
        }


        //如果状态是1就表示提交，要进行审核  发消息到mq
        if(wmNewsDtoSave.getStatus() != 0) {
            kafkaTemplate.send(BusinessConstants.ContentAudit.Content_Audit,wmNews.getId().toString());
        }





    }


    /**
     *
     * 根据id获取文章
     */
    @Override
    public WmNewsDtoSave getWmNewsById(int id) {
        WmNewsDtoSave wmNewsDtoSave = new WmNewsDtoSave();
        WmNews wmNews = getById(id);

        BeanUtils.copyProperties(wmNews,wmNewsDtoSave);
        wmNewsDtoSave.setUserId(wmNews.getUserId());
        if(!StringUtils.isEmpty(wmNews.getContent())) {

            wmNewsDtoSave.setContent(JSON.parseArray(wmNews.getContent(), ContentVo.class));
        }
        if(!StringUtils.isEmpty(wmNews.getImages())){
            wmNewsDtoSave.setImages(Arrays.asList(wmNews.getImages().split(",")));
        }

        return wmNewsDtoSave;
    }


    /**
     * 用来获取文本中的图片个数
     * @param wmNewsDtoSave
     * @return
     */
    private List<String> getImageSize(WmNewsDtoSave wmNewsDtoSave){
        List<String> list = new ArrayList<>();
        List<ContentVo> content = wmNewsDtoSave.getContent();
        for (ContentVo contentVo : content) {
            if("image".equals(contentVo.getType())){
                list.add(contentVo.getValue());
            }
        }
        return list;
    }



    /**
     * 用于查询未审核通过的和人工审核列表，进行人工审核
     * @param title  查询条件
     * @return
     */
    @Override
    public PageInfo<WmNewsSearchVo> findWmNewsAudit(Long page, Long pagesize, String title) {

        PageInfo<WmNewsSearchVo> pageInfo = new PageInfo<>();
    //查询记录
    List<WmNewsSearchVo> list = wmNewsMapper.findWmNewsAudit((page - 1) * pagesize,pagesize,"%"+title+"%");


    //查询总记录数
     long count = wmNewsMapper.count(title);

       long totalPages = (long) Math.ceil(count / pagesize);

        pageInfo.setPage(page);
        pageInfo.setSize(pagesize);
        pageInfo.setTotalPages(totalPages);
        pageInfo.setTotal(count);
        pageInfo.setList(list);
    return pageInfo;
 }




}
