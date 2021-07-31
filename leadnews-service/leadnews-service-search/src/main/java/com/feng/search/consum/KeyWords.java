package com.feng.search.consum;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.behaviour.feign.ApBehaviorEntryFeign;
import com.feng.behaviour.pojo.ApBehaviorEntry;
import com.feng.common.ulits.BusinessConstants;
import com.feng.search.mapper.ApUserSearchMapper;
import com.feng.search.pojo.ApUserSearch;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 关键字保存
 */
@Component
public class KeyWords {

    @Autowired
    private ApBehaviorEntryFeign apBehaviorEntryFeign;
    @Autowired
    private ApUserSearchMapper apUserSearchMapper;
    /**
     * 用来接收保存用户搜索的关键字
     * @param record
     */
    @KafkaListener(topics=BusinessConstants.ContentAudit.KEY_WORD_TOPIC)
    public void SaveKeyWords(ConsumerRecord<String,String> record){
        String value = record.value();
        Map<String,String> map = JSON.parseObject(value, Map.class);
        Integer id = Integer.valueOf(map.get("id"));
        //根据用户id查询行为实体表的实体id
        ApBehaviorEntry behaviorEntry = apBehaviorEntryFeign.findEntry(1, id).getData();
        if(behaviorEntry == null){
            return;
        }
        //向搜索词表保存记录
        //先根据关键字和id查询是否已经存在，存在则不需要保存
        QueryWrapper<ApUserSearch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entry_id",behaviorEntry.getId());
        queryWrapper.eq("keyword",map.get("keywords"));
        ApUserSearch apUserSearch = apUserSearchMapper.selectOne(queryWrapper);
        //如果查不到则添加记录，如果相同则不添加记录
        if(apUserSearch == null){

            apUserSearch = new ApUserSearch();
            apUserSearch.setEntryId(behaviorEntry.getId());
            apUserSearch.setKeyword(map.get("keywords"));
            apUserSearch.setCreatedTime(LocalDateTime.now());
            apUserSearch.setStatus(1);
            apUserSearchMapper.insert(apUserSearch);
        }
    }
}
