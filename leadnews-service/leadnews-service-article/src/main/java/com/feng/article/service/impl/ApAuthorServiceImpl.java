package com.feng.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.article.mapper.ApAuthorMapper;
import com.feng.article.pojo.ApAuthor;
import com.feng.article.service.ApAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP文章作者信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Service
public class ApAuthorServiceImpl extends ServiceImpl<ApAuthorMapper, ApAuthor> implements ApAuthorService {
    @Autowired
    private ApAuthorMapper apAuthorMapper;


    /**
     * 创建作者
     * @param apAuthor
     * @return
     */
    @Override
    public void saveAuthor(ApAuthor apAuthor) {

        /**
         * 先查询是否有作者记录
         */
        QueryWrapper<ApAuthor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",apAuthor.getUserId());
        ApAuthor selectOne = apAuthorMapper.selectOne(queryWrapper);

        if(selectOne == null){
            apAuthorMapper.insert(apAuthor);
        }

    }
}
