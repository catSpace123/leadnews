package com.feng.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.article.pojo.ApAuthor;

/**
 * <p>
 * APP文章作者信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
public interface ApAuthorService extends IService<ApAuthor> {

    /**
     * 创建作者
     * @param apAuthor
     * @return
     */
    void saveAuthor(ApAuthor apAuthor);
}
