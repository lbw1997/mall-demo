package com.abkm.mall.demo.module.ums.service;

import com.abkm.mall.demo.module.ums.model.UmsAdmin;
import com.abkm.mall.demo.module.ums.model.UmsResource;

import java.util.List;


/**
 * 后台缓存管理类
 */
public interface UmsAdminCacheService {

    /**
     * 获取缓存用户后台信息
     */
    UmsAdmin getAdmin(String username);

    /**
     * 缓存用户后台信息
     */
    void setAdmin(UmsAdmin umsAdmin);

    /**
     * 获取缓存中用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 缓存用户资源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
