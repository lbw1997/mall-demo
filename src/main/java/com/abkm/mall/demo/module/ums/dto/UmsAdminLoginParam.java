package com.abkm.mall.demo.module.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * description: 用户登录参数 <br>
 * date: 2020/9/24 19:06 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {

    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
