package com.feng.article.mqListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.article.pojo.ApArticle;
import com.feng.article.pojo.ApArticleConfig;
import com.feng.article.service.ApArticleConfigService;
import com.feng.article.service.ApArticleService;
import com.feng.common.ulits.BusinessConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import javax.swing.text.html.parser.Entity;
import java.util.Map;

/**
 * 用于同步是否上下架的操作
 */
@Component
public class MediaNewsSyncListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    @KafkaListener(topics = BusinessConstants.ContentAudit.WM_NEWS_DOWN_OR_UP_TOPIC)
    public void mediaNewsSyncListener(ConsumerRecord<String,String> record){

        String value = record.value();
        Map<String,String> map = JSON.parseObject(value, Map.class);

        //这是文章id

        Long id = Long.valueOf(map.get("id"));

        Integer enable = Integer.valueOf(map.get("enable"));


        //同步文章配置表中的状态
       // 根据文章id同步状态
        QueryWrapper<ApArticleConfig> up = new QueryWrapper<>();
        up.eq("article_id",id);

        ApArticleConfig entity = new ApArticleConfig();
        //如果是1就下架


            entity.setIsDown(enable);
        apArticleConfigService.update(entity,up);
    }

}
