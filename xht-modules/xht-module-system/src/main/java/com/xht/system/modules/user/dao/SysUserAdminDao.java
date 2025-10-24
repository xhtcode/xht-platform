package com.xht.system.modules.user.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.user.domain.entity.SysUserAdminEntity;

import java.util.Optional;

/**
 * 管理员用户信息 Dao
 *
 * @author xht
 **/
public interface SysUserAdminDao extends MapperRepository<SysUserAdminEntity> {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户id
     */
    Optional<SysUserAdminEntity> getUserInfoByUserId(Long userId);
}
