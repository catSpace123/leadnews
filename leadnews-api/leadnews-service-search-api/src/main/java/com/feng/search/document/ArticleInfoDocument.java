package com.feng.search.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
//固定为 “_doc”,配置也不会生效
@Document(indexName = "article")
public class ArticleInfoDocument implements Serializable {

    @Id
    //需要将long类型的数据转成字符串，因为es中存储的都是字符串
   // @JsonSerialize(using = Long2StringSerializer.class)
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String title;

    private Integer authorId;

    private String authorName;

    private Integer channelId;

    private String channelName;

    private Integer layout;

    private String images;

    private Integer likes;

    private Integer collection;

    private Integer comment;

    private Integer views;


    private LocalDateTime createdTime;

    private LocalDateTime publishTime;


}