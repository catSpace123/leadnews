package com.feng.behaviour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.behaviour.mapper.ApBehaviorEntryMapper;
import com.feng.behaviour.pojo.ApBehaviorEntry;
import com.feng.behaviour.service.ApBehaviorEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-20
 */
@Service
public class ApBehaviorEntryServiceImpl extends ServiceImpl<ApBehaviorEntryMapper, ApBehaviorEntry> implements ApBehaviorEntryService {

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;
    /**
     * 根据设备id获取实体

     * @param type 是设备还是用户
     * @param equipmentId
     * @return
     */
    @Override
    public ApBehaviorEntry findEntry(Integer type, Integer equipmentId) {

        QueryWrapper<ApBehaviorEntry> querywrapper = new QueryWrapper<>();

        querywrapper.eq("entry_id",equipmentId);
        querywrapper.eq("type",type);

        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectOne(querywrapper);

        return apBehaviorEntry;
    }
}
