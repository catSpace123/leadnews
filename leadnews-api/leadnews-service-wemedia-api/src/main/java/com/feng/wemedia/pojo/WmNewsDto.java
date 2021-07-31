package com.feng.wemedia.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 自媒体内容文章查询接收vo
 */
@Data
@ApiModel(description = "自媒体内容文章查询接收vo",value = "WmNewsDto")
public class WmNewsDto extends WmNews {

    @ApiModelProperty(notes = "开始时间")
    private LocalDateTime startTime;
    @ApiModelProperty(notes = "结束时间")
    private LocalDateTime endTime;
}
