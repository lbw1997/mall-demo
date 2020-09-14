package com.abkm.mall.demo.module.ums.service;

import com.abkm.mall.demo.module.ums.entity.UmsAdmin;
import com.abkm.mall.demo.module.ums.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author abkm
 * @since 2020-09-13
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取指定用户的可访问源
     */
    List<UmsResource> getResourceList(Long adminId);
}
