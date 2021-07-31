package com.feng.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.article.mapper.ApArticleConfigMapper;
import com.feng.article.pojo.ApArticleConfig;
import com.feng.article.service.ApArticleConfigService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP已发布文章配置表 服务实现类
 * </p>
 *
 * @author
 * @since 2021-07-09
 */
@Service
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService {

}
