package com.abkm.mall.demo.config;

import com.abkm.mall.demo.module.ums.entity.UmsResource;
import com.abkm.mall.demo.module.ums.service.UmsAdminService;
import com.abkm.mall.demo.module.ums.service.UmsResourceService;
import com.abkm.mall.demo.security.compoent.DynamicSecurityService;
import com.abkm.mall.demo.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description: MySecurityConfig <br>
 * date: 2020/9/13 19:56 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends SecurityConfig {


    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    /**
     *  动态更新路径
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String,ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> list = resourceService.list();
                for (UmsResource resource:list) {
                    map.put(resource.getUrl(),
                        new org.springframework.security.access.SecurityConfig(resource.getId()+":"+resource.getName()));
                }
                return map;
            }
        };
    }
}
