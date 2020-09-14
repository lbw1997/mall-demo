package com.abkm.mall.demo.module.ums.mapper;

import com.abkm.mall.demo.module.ums.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author abkm
 * @since 2020-09-13
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
    /**
     *  根据用户Id获取访问资源列表
     * @param adminId
     * @return
     */
    List<UmsResource> getResourcesList(@Param("adminId")Long adminId);

    /**
     * 根据角色名获取访问资源列表
     * @param roleId
     * @return
     */
    List<UmsResource> getResourceListByRoleId(@Param("roleId")Long roleId);
}
