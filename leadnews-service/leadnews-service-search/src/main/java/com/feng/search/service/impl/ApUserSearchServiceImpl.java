package com.feng.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.pojo.PageInfo;
import com.feng.common.ulits.BusinessConstants;
import com.feng.common.ulits.GetUserIdHeaderUtil;
import com.feng.search.document.ArticleInfoDocument;
import com.feng.search.dto.SearchDto;
import com.feng.search.mapper.ApUserSearchMapper;
import com.feng.search.pojo.ApUserSearch;
import com.feng.search.repository.ArticleInfoDocumentRepository;
import com.feng.search.service.ApUserSearchService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.MoreLikeThisQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * APP用户搜索信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-07-22
 */
@Service
public class ApUserSearchServiceImpl extends ServiceImpl<ApUserSearchMapper, ApUserSearch> implements ApUserSearchService {

    @Autowired
    private ArticleInfoDocumentRepository articleInfoDocumentRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;
    /**
     * 用来后台审核通过后将自媒体的文章保存到es中
     * @param articleInfoDocument
     * @return
     */
    @Override
    public void saveEs(ArticleInfoDocument articleInfoDocument) {

        //调用es将用户发布的文章传入es中
        if(articleInfoDocument != null){

            articleInfoDocumentRepository.save(articleInfoDocument);
        }
    }


    /**
     *  文章搜索
     * @param searchDto
     * @return
     */
    @Override
    public PageInfo<ArticleInfoDocument> search(SearchDto searchDto) {

        String keywords = searchDto.getKeywords();
        //如果搜索为空就给默认的值
        if(keywords == null){
            keywords = "吆西";
        }
        //判断是否符合规范
        Integer page = searchDto.getPage();
        if(page <= 0  ){
            page = 1;
        }

        Integer size = searchDto.getSize();
        if(size >= 20 || size <= 0){
            size = 10;
        }

        Map<String,String> map = new HashMap<>();
        map.put("id", GetUserIdHeaderUtil.getUserId());
        map.put("keywords",keywords);
        //发消息到kafka保存搜索关键字到数据库表中
        kafkaTemplate.send(BusinessConstants.ContentAudit.KEY_WORD_TOPIC, JSON.toJSONString(map));

        //创建查询对象
        NativeSearchQueryBuilder nativeSearchQuery = new NativeSearchQueryBuilder();
        //构建条件对象
        nativeSearchQuery.withQuery(QueryBuilders.matchQuery("title",keywords));
        //1设置高亮条件
        nativeSearchQuery.withHighlightFields(new HighlightBuilder.Field("title"));
        nativeSearchQuery.withHighlightBuilder(new HighlightBuilder().preTags("'<em style='color:'red'>").postTags("</em>"));
        //设置分页条件
        Pageable pageable = PageRequest.of(page-1,size);
        nativeSearchQuery.withPageable(pageable);
        //设置排序条件
        nativeSearchQuery.withSort(SortBuilders.fieldSort("publishTime"));
        //构建查询对象
        NativeSearchQuery query = nativeSearchQuery.build();

        //执行查询对象
        SearchHits<ArticleInfoDocument> searchHits = elasticsearchRestTemplate.search(query, ArticleInfoDocument.class, IndexCoordinates.of("article"));
        //todo
        PageInfo<ArticleInfoDocument> pageInfo = new PageInfo<>();
        List<ArticleInfoDocument> list = new ArrayList<>();
        //获取内容
        for (SearchHit<ArticleInfoDocument> searchHit : searchHits) {
            //获取查询到的文本
            ArticleInfoDocument content = searchHit.getContent();
            //获取高亮字段
            List<String> title = searchHit.getHighlightField("title");
            //如果不为空就替换字段
            if(!CollectionUtils.isEmpty(title)){
                content.setTitle(title.get(0));
                list.add(content);
            }
        }
        //总记录数
        long totalHits = searchHits.getTotalHits();
        //总页数
        long pagesize = totalHits / size + (totalHits % size  > 0 ? 1 : 0 );
        pageInfo.setList(list);
        pageInfo.setPage(page.longValue());
        pageInfo.setSize(size.longValue());
        pageInfo.setTotal(totalHits);
        pageInfo.setTotalPages(pagesize);

        return pageInfo;
    }
}
