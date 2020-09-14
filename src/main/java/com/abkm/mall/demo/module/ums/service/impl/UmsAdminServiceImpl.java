package com.abkm.mall.demo.module.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.abkm.mall.demo.domain.AdminUserDetails;
import com.abkm.mall.demo.module.ums.entity.UmsAdmin;
import com.abkm.mall.demo.module.ums.entity.UmsResource;
import com.abkm.mall.demo.module.ums.mapper.UmsAdminMapper;
import com.abkm.mall.demo.module.ums.mapper.UmsResourceMapper;
import com.abkm.mall.demo.module.ums.service.UmsAdminCacheService;
import com.abkm.mall.demo.module.ums.service.UmsAdminService;
import com.abkm.mall.demo.module.ums.service.UmsResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author abkm
 * @since 2020-09-13
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Autowired
    private UmsResourceMapper resourceMapper;

    /**
     * 加载用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin umsAdmin = adminCacheService.getUmsAdmin(username);
        if (umsAdmin != null) {
            List<UmsResource> resourceList = getResourceList(umsAdmin.getId());
            return new AdminUserDetails(umsAdmin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 获取指定用户的数据源
     */
    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        //先读缓存
        List<UmsResource> resourceList = adminCacheService.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        //再读数据库
        resourceList = resourceMapper.getResourcesList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            //添加到缓存中
            adminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }
}
