package com.feng.admin.service;

/**
 *自媒体文章审核业务接口
 */
public interface MqContentAuditService  {

    /**
     * 审核图文
     * @param wmId
     */
    void contentAudit(Integer wmId) throws Exception;
}
