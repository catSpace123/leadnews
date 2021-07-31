package com.feng.search.repository;

import com.feng.search.document.ArticleInfoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleInfoDocumentRepository extends ElasticsearchRepository<ArticleInfoDocument,Long> {

}