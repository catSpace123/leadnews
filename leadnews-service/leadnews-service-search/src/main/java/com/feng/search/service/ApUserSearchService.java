package com.feng.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.pojo.PageInfo;
import com.feng.search.document.ArticleInfoDocument;
import com.feng.search.dto.SearchDto;
import com.feng.search.pojo.ApUserSearch;

/**
 * <p>
 * APP用户搜索信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-07-22
 */
public interface ApUserSearchService extends IService<ApUserSearch> {

    /**
     * 用来后台审核通过后将自媒体的文章保存到es中
     * @param articleInfoDocument
     * @return
     */
    void saveEs(ArticleInfoDocument articleInfoDocument);


    /**
     * 文章搜索
     */
    PageInfo<ArticleInfoDocument> search(SearchDto searchDto);
}
