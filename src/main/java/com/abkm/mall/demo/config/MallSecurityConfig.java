package com.abkm.mall.demo.config;

import com.abkm.mall.demo.module.ums.service.UmsAdminService;
import com.abkm.mall.demo.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Security模块配置类
 */
@EnableWebSecurity
public class MallSecurityConfig extends SecurityConfig {

    @Autowired
    private UmsAdminService adminService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }
}
