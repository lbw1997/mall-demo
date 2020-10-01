package com.abkm.mall.demo.module.ums.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * description: UmsAdminExportVo <br>
 * date: 2020/10/1 15:31 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminExportVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    @Excel(name = "用户名",orderNum = "0",width = 15)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    @Excel(name = "密码",orderNum = "1",width = 70)
    private String password;

    @ApiModelProperty(value = "用户头像")
    @Excel(name = "用户头像",orderNum = "2",width = 15)
    private String icon;

    @Email
    @ApiModelProperty(value = "邮箱")
    @Excel(name = "邮箱",orderNum = "3",width = 30)
    private String email;

    @ApiModelProperty(value = "用户昵称")
    @Excel(name = "用户昵称",orderNum = "4",width = 15)
    private String nickName;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注",orderNum = "6",width = 30)
    private String note;
}
