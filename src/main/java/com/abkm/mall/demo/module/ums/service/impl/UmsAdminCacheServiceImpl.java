package com.abkm.mall.demo.module.ums.service.impl;

import com.abkm.mall.demo.common.service.RedisService;
import com.abkm.mall.demo.module.ums.entity.UmsAdmin;
import com.abkm.mall.demo.module.ums.entity.UmsResource;
import com.abkm.mall.demo.module.ums.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * description: UmsAdminCacheServiceImpl <br>
 * date: 2020/9/14 10:46 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    /**
     * 根据username获取admin信息
     * @param username
     * @return
     */
    @Override
    public UmsAdmin getUmsAdmin(String username) {
        String key = REDIS_DATABASE+":"+REDIS_KEY_ADMIN+":"+username;
        return (UmsAdmin)redisService.get(key);
    }

    /**
     * 根据adminId获取资源列表
     */
    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE+":"+REDIS_KEY_RESOURCE_LIST+":"+adminId;
        return (List<UmsResource>)redisService.get(key);
    }

    /**
     * 根据adminId缓存资源列表
     */
    @Override
    public void setResourceList(Long adminId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE+":"+REDIS_KEY_RESOURCE_LIST+":"+adminId;
        redisService.set(key,resourceList,REDIS_EXPIRE);
    }
}
