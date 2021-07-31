package com.feng.wemedia.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章内容类型
 */
@Data
public class ContentVo {
    @ApiModelProperty(notes = "类型可以是：text，image")
    private  String type;

    @ApiModelProperty(notes = "内容")
    private  String value;
}
