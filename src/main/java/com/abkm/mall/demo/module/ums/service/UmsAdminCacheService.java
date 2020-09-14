package com.abkm.mall.demo.module.ums.service;

import com.abkm.mall.demo.module.ums.entity.UmsAdmin;
import com.abkm.mall.demo.module.ums.entity.UmsResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description: UmsAdminCacheService <br>
 * date: 2020/9/14 10:46 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
@Service
public interface UmsAdminCacheService {

    /**
     * 从缓存中获取admin
     */
    public UmsAdmin getUmsAdmin(String username);

    /**
     * 根据adminId从缓存中获取对应资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 根据adminId向缓存中添加对应的资源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
