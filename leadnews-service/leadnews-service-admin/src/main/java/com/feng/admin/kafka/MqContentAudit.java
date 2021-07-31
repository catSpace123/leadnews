package com.feng.admin.kafka;

import com.feng.admin.service.MqContentAuditService;
import com.feng.common.ulits.BusinessConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 内容审核消费者
 */

@Component
@Slf4j
public class MqContentAudit {

    @Autowired
    private MqContentAuditService mqContentAuditService;


    @KafkaListener(topics = BusinessConstants.ContentAudit.Content_Audit)
    public void ContentAudit(ConsumerRecord<String,String> record){
        /**
         * 获取消息
         *
         */
        try {
            if(record != null ){
                //自媒体文章id
                Integer wmId = Integer.valueOf(record.value());

                /**
                 * 调用业务进行审核
                 */
                mqContentAuditService.contentAudit(wmId);

            }
        } catch (Exception e) {
            log.error(e.getMessage());
            //e.printStackTrace();
        }
    }
}
