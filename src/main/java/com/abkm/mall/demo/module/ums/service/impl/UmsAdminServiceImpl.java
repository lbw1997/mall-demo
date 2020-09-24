package com.abkm.mall.demo.module.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.abkm.mall.demo.domain.AdminUserDetails;
import com.abkm.mall.demo.module.ums.mapper.UmsAdminLoginLogMapper;
import com.abkm.mall.demo.module.ums.mapper.UmsResourceMapper;
import com.abkm.mall.demo.module.ums.model.UmsAdmin;
import com.abkm.mall.demo.module.ums.mapper.UmsAdminMapper;
import com.abkm.mall.demo.module.ums.model.UmsAdminLoginLog;
import com.abkm.mall.demo.module.ums.model.UmsResource;
import com.abkm.mall.demo.module.ums.service.UmsAdminCacheService;
import com.abkm.mall.demo.module.ums.service.UmsAdminService;
import com.abkm.mall.demo.security.util.JwtTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author abkm
 * @since 2020-09-24
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsResourceMapper resourceMapper;
    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;
    /**
     * 获取用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 获取指定用户的可访问资源
     */
    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        //从缓存中查询，存在就直接返回
        List<UmsResource> resourceList = adminCacheService.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        //再从数据库中读取，并存入缓存中
        try {
            resourceList = resourceMapper.getResourceList(adminId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(CollUtil.isNotEmpty(resourceList)){
            adminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    //登录功能
    @Override
    public String login(String username, String password) {

        String token = null;
        try {
            //密码加密后进行比对
            UserDetails userDetails = loadUserByUsername(username);

            if (!passwordEncoder.matches(password,userDetails.getPassword())) {
                Assert.fail("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                Assert.fail("账号已被禁用");
            }
            //通过认证添加到SecurityContext中
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            //根据用户信息获取token
            token = jwtTokenUtil.generatorToken(userDetails);
            //登录成功添加记录
            insertLoginLog(username);
        } catch (Exception e) {
            LOGGER.warn("登录异常:{}"+username);
        }
        return token;
    }

    /**
     * 添加登录信息
     */
    private void insertLoginLog(String username) {
        //通过用户名获取用户信息
        UmsAdmin admin = getAdminByUsername(username);
        if (admin == null) return;
        //创建用户登录信息类，补全相关属性
        UmsAdminLoginLog adminLoginLog = new UmsAdminLoginLog();
        adminLoginLog.setAdminId(admin.getId());
        adminLoginLog.setCreateTime(new Date());
        //通过ServletRequestAttributes获取ip地址
        ServletRequestAttributes attributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        adminLoginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(adminLoginLog);
    }

    /**
     * 根据用户名获取后台管理员
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        //从缓存中获取用户信息
        UmsAdmin admin = adminCacheService.getAdmin(username);
        if (admin != null) return admin;
        //没有，则从数据库中读取
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername,username);
        List<UmsAdmin> adminList = list(wrapper);
        if (adminList!=null && adminList.size()>0) {
            admin = adminList.get(0);
            //添加缓存
            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }

}
