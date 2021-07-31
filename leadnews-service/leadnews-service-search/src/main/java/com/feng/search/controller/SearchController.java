package com.feng.search.controller;

import com.feng.common.pojo.PageInfo;
import com.feng.common.pojo.Result;
import com.feng.search.document.ArticleInfoDocument;
import com.feng.search.dto.SearchDto;
import com.feng.search.service.ApUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章的搜索
 * */
@RestController
@RequestMapping("/articleSearch")
public class SearchController {

    @Autowired
    private ApUserSearchService apUserSearchService;

    /**
    * 文章搜索
     */
    @PostMapping("/search")
    public Result<PageInfo<ArticleInfoDocument>> search(@RequestBody SearchDto searchDto){

        PageInfo<ArticleInfoDocument> pageInfo =  apUserSearchService.search(searchDto);

        return Result.ok(pageInfo);
    }
}
