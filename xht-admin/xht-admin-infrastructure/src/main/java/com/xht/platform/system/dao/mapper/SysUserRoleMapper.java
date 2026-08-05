package com.xht.platform.system.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import  com.xht.platform.system.entity.SysRoleEntity;
import  com.xht.platform.system.entity.SysUserRoleEntity;
import  com.xht.platform.system.enums.RoleStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关系
 *
 * @author xht
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapperX<SysUserRoleEntity> {

    /**
     * 根据用户ID获取角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> getRoleCodes(@Param("userId") Long userId);

    /**
     * 根据用户ID获取角色ID列表
     *
     * @param roleStatus 角色状态枚举
     * @param userId     用户ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdByUserId(RoleStatusEnum roleStatus, String userId);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleEntity> findRoleListByUserId(Long userId);
}




