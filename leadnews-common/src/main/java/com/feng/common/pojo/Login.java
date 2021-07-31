package com.feng.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于用户登录
 */
@Data
@ApiModel(value = "Login",description = "后台登录实体")
public class Login {
    @ApiModelProperty(notes = "用户名",required = true)
    private String name;
    @ApiModelProperty(notes = "密码",required = true)
    private String password;
    @ApiModelProperty(notes = "验证码",required = true)
    private String code;
}
