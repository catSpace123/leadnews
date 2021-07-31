package com.feng.behaviour.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.behaviour.pojo.ApBehaviorEntry;

/**
 * <p>
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
public interface ApBehaviorEntryService extends IService<ApBehaviorEntry> {

    /**
     * 根据设备id获取实体
     *
     *
     * @param type
     * @param equipmentId
     * @return
     */
    ApBehaviorEntry findEntry(Integer type, Integer equipmentId);
}
