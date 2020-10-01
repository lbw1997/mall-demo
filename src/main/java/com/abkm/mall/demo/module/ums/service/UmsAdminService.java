package com.abkm.mall.demo.module.ums.service;

import com.abkm.mall.demo.module.ums.dto.UmsAdminParam;
import com.abkm.mall.demo.module.ums.model.UmsAdmin;
import com.abkm.mall.demo.module.ums.model.UmsResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     *  根据用户名或昵称分页查询用户信息
     */
    Page<UmsAdmin> getUmsAdminList(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 删除指定用户
     */
    boolean delete(Long id);

    /**
     * 指定Id修改用户信息
     */
    boolean update(Long id, UmsAdmin umsAdmin);

    /**
     * 导出用户信息
     */
    boolean exportAdmin(HttpServletResponse response);

    /**
     * 导入用户信息
     */
    boolean importAdmin(MultipartFile file);
}
