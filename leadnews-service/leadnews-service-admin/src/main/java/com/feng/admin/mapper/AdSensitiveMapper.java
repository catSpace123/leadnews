package com.feng.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.admin.pojo.AdSensitive;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 敏感词信息表 Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2021-07-08
 */
public interface AdSensitiveMapper extends BaseMapper<AdSensitive> {

    /**
     * 敏感词信息表查询
     * @return
     */
    @Select("select sensitives from ad_sensitive")
    List<String> findSensitive();
}
