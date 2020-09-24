package com.abkm.mall.demo.module.ums.service;

import com.abkm.mall.demo.module.ums.model.UmsAdmin;
import com.abkm.mall.demo.module.ums.model.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author abkm
 * @since 2020-09-24
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 加载用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 登录功能
     * @param username  用户名
     * @param password  密码
     * @return  生成JWT的token
     */
    String login(String username, String password);

    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 获取指定用户的可访问资源
     */
    List<UmsResource> getResourceList(Long adminId);
}
