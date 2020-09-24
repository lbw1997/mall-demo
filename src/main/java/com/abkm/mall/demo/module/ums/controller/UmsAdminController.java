package com.abkm.mall.demo.module.ums.controller;


import com.abkm.mall.demo.common.api.CommonResult;
import com.abkm.mall.demo.module.ums.dto.UmsAdminLoginParam;
import com.abkm.mall.demo.module.ums.service.UmsAdminService;
import com.abkm.mall.demo.module.ums.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author abkm
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "UmsAdminController",description = "后台用户管理")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsRoleService roleService;

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam loginParam) {
        String token = adminService.login(loginParam.getUsername(),loginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }
}

