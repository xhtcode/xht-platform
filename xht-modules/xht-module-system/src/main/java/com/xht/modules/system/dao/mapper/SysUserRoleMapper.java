package com.xht.modules.system.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.common.enums.RoleStatusEnums;
import com.xht.modules.system.domain.entity.SysRoleEntity;
import com.xht.modules.system.domain.entity.SysUserRoleEntity;
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
    List<Long> selectRoleIdByUserId(RoleStatusEnums roleStatus, String userId);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleEntity> findRoleListByUserId(Long userId);
}




