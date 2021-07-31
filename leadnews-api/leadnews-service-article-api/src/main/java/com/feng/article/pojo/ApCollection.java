package com.feng.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP收藏信息表
 * </p>
 *
 * @author ljh
 * @since 2021-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_collection")
@ApiModel(value="ApCollection", description="APP收藏信息表")
public class ApCollection implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "行为实体的主键ID值")
    @TableField("entry_id")
    private Integer entryId;

    @ApiModelProperty(value = "文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(value = "点赞内容类型	            0文章	            1动态")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @TableField("collection_time")
    private LocalDateTime collectionTime;

    @ApiModelProperty(value = "发布时间")
    @TableField("published_time")
    private LocalDateTime publishedTime;


}
