package com.abkm.mall.demo.module.ums.mapper;

import com.abkm.mall.demo.module.ums.model.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author abkm
 * @since 2020-09-24
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);
}
