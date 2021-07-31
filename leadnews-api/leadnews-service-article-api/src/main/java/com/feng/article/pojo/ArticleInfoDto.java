package com.feng.article.pojo;

import lombok.Data;

/**
 * 用来审核自媒体通过后保存文章信息的pojo
 */
@Data
public class ArticleInfoDto {

    private ApArticle apArticle;

    private ApArticleConfig apArticleConfig;

    private ApArticleContent apArticleContent;
}
