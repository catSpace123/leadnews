package com.feng.wemedia.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
     * @description:  用于返回前端的人工审核和审核不通过的数据返回
     * @author: Cat
     * @date:  13:24
     * @param: null
     * @return:
 */
@Data
public class WmNewsSearchVo extends WmNews{
    @ApiModelProperty(notes = "作者名称")
    private String authorName;
}
