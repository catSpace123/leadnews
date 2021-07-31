package com.feng.behaviour.consume;

import com.alibaba.fastjson.JSON;
import com.feng.behaviour.dto.FollowBehaviorDto;
import com.feng.behaviour.service.ApFollowBehaviorService;
import com.feng.common.ulits.BusinessConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 关注行为消费者
 */
@Component
public class BehaviourConsume {

    @Autowired
    private ApFollowBehaviorService apFollowBehaviorService;

    @KafkaListener(topics = BusinessConstants.ContentAudit.AP_USER_BEHAVIOUR_OR_UP_TOPIC)
    public void behaviourConsume(ConsumerRecord<String,String> record){
        //获取消息
        String value = record.value();
        FollowBehaviorDto followBehaviorDto = JSON.parseObject(value, FollowBehaviorDto.class);

        if(followBehaviorDto != null){
            //调用service添加记录
            apFollowBehaviorService.saveFollow(followBehaviorDto);

        }
    }
}
