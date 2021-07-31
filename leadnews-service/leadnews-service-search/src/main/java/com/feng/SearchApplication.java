package com.feng;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.feng.article.feign.ApArticleFeign;
import com.feng.article.pojo.ApArticle;
import com.feng.search.document.ArticleInfoDocument;
import com.feng.search.repository.ArticleInfoDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
* @author fengSir
* @version 1.0
* @date 2021/5/2 11:36
* @description 标题
* @package
*/
@SpringBootApplication
@MapperScan(basePackages = "com.feng.search.mapper")//扫描mapper接口所在的包即可
@EnableDiscoveryClient
@Slf4j
@EnableFeignClients("com.feng.*.feign")
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }

    @Autowired
    private ApArticleFeign apArticleFeign;
    /**
     * 添加一个mybatis-plus的插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Autowired
    private ArticleInfoDocumentRepository ArticleInfoDocumentRepository;
    /**
     * 微服务初始化时调用 用来将数据从数据库同步到ES中
     */
    @PostConstruct
    public void saveSearchES(){
        log.debug("哈哈启动啦");
        System.out.println("启动啦");
        //从文章微服务中获取文章信息存入到Es中
        List<ApArticle> apArticles = apArticleFeign.findAll().getData();
        //判断是否为空
        if(CollectionUtils.isEmpty(apArticles)){
            log.debug("表中无数据");
            return;
        }


        List<ArticleInfoDocument> articleInfoDocuments = JSON.parseArray(JSON.toJSONString(apArticles), ArticleInfoDocument.class);

        ArticleInfoDocumentRepository.saveAll(articleInfoDocuments);

    }

}