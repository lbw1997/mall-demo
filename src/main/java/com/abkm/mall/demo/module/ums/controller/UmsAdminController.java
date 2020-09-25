package com.abkm.mall.demo.module.ums.controller;


import com.abkm.mall.demo.common.api.CommonPage;
import com.abkm.mall.demo.common.api.CommonResult;
import com.abkm.mall.demo.module.ums.dto.UmsAdminLoginParam;
import com.abkm.mall.demo.module.ums.dto.UmsAdminParam;
import com.abkm.mall.demo.module.ums.model.UmsAdmin;
import com.abkm.mall.demo.module.ums.service.UmsAdminService;
import com.abkm.mall.demo.module.ums.service.UmsRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public CommonResult register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed("注册失败");
        }
        return CommonResult.success(umsAdmin);
    }

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

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation(value = "获取指定用户信息")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult getUmsAdminById(@PathVariable Long id) {
        UmsAdmin umsAdmin = adminService.getById(id);
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult<CommonPage<UmsAdmin>> getUmsAdminList(@RequestParam(value = "keyword",required = false)String keyword,
                                                              @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                                              @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum) {
        Page<UmsAdmin> adminList = adminService.getUmsAdminList(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation(value = "删除指定用户信息")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public CommonResult delete(@PathVariable Long id) {
        boolean success = adminService.delete(id);
        if (success) {
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改指定用户信息")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,@RequestBody UmsAdmin umsAdmin) {
        boolean success = adminService.update(id,umsAdmin);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}

