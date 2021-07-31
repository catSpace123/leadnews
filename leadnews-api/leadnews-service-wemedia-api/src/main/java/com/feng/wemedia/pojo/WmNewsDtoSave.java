package com.feng.wemedia.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "WmNewsDtoSave",description = "用来接收和返回文章信息的pojo")
public class WmNewsDtoSave {
    /**
     * 主键ID
     */
    @ApiModelProperty(notes = "主键id")
    private Integer id;

    /**
     * 文章标题
     */
    @ApiModelProperty(notes = "标题")
    private String title;

    /**
     * 图文内容
     */
    @ApiModelProperty(notes = "图文内容")
    private List<ContentVo> content;

    /**
     * 指定为封面类型  0 是无图  1 是单图  3 是多图  -1 是自动
     */
    @ApiModelProperty(notes = "封面类型")
    private Integer type;

    /**
     * 指定选中的频道ID
     */
    @ApiModelProperty(notes = "指定选中的频道ID")
    private Integer channelId;

    /**
     * 指定标签
     */

    private String labels;

    /**
     * 状态 0 草稿  1 提交 待审核 （该字段可以不用设置,前端不必传递）
     */
    @ApiModelProperty(notes = "状态")
    private Integer status;

    /**
     * 定时发布时间
     */
    @ApiModelProperty(notes = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime publishTime;

    /**
     * 封面图片
     */
    @ApiModelProperty(notes = "封面图片")
    private List<String> images;

    @ApiModelProperty(notes = "作者名称")
    private String authorName;

    @ApiModelProperty(value = "自媒体用户ID")
    private Integer userId;
}