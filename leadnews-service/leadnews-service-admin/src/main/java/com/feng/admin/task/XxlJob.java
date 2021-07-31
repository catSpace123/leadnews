package com.feng.admin.task;


import com.feng.admin.service.MqContentAuditService;
import com.feng.admin.service.impl.MqContentAuditServiceImpl;
import com.feng.article.pojo.ApArticle;
import com.feng.wemedia.feign.WmNewsFeign;
import com.feng.wemedia.pojo.WmNews;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
     * @description: 定时任务定时发表文章
     * @author: Cat
     * @date: 2021/7/17 19:34
     * @param: null
     * @return:
 */
@Component
@Slf4j
public class XxlJob {

    @Autowired
    private WmNewsFeign wmNewsFeign;

    @Autowired
    private MqContentAuditServiceImpl mqContentAuditService;

    @com.xxl.job.core.handler.annotation.XxlJob("ArticleReview")
    public ReturnT<String> demoJobHandler(String param) throws Exception {

        //查询自媒体文章表中的状态=8 的文章列表进行对比时间发布

        List<WmNews> wmNews = wmNewsFeign.fundState(8).getData();

        System.out.println(param);
        if(!CollectionUtils.isEmpty(wmNews)){
            for (WmNews wmNew : wmNews) {
                //获取发布时间
                LocalDateTime publishTime = wmNew.getPublishTime();
                //当前时间
                LocalDateTime now = LocalDateTime.now();
                if(publishTime == null){
                    //修改状态 发布
                    WmNews record = new WmNews();
                    record.setId(wmNew.getId());
                    record.setStatus(9);
                    wmNewsFeign.updateByPrimaryKey(record);

                    //向文章表添加记录
                    wmNew.setPublishTime(now);
                    mqContentAuditService.getApArticleAndSave(wmNew);

                }else{

                    if(now.isAfter(publishTime) || now.isEqual(publishTime)){
                        //修改状态 发布
                        WmNews record = new WmNews();
                        record.setId(wmNew.getId());
                        record.setStatus(9);
                        wmNewsFeign.updateByPrimaryKey(record);

                        //向文章表添加记录
                        wmNew.setPublishTime(now);
                        mqContentAuditService.getApArticleAndSave(wmNew);

                    }else{
                        System.out.println("没有任务可执行");
                    }
                }

            }
        }

        return ReturnT.SUCCESS;
    }
}
