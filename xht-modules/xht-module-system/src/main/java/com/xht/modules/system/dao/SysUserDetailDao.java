package com.xht.modules.system.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.system.domain.entity.SysUserDetailEntity;
import com.xht.modules.system.domain.entity.SysUserEntity;

/**
 * 管理员用户信息 Dao
 *
 * @author xht
 **/
public interface SysUserDetailDao extends MapperRepository<SysUserDetailEntity> {

    /**
     * 保存用户信息
     *
     * @param sysUserEntity      用户信息
     * @param sysUserDetailEntity 用户详细信息
     */
    void saveUserInfo(SysUserEntity sysUserEntity, SysUserDetailEntity sysUserDetailEntity);

    /**
     * 更新用户信息
     *
     * @param sysUserEntity      用户信息
     * @param sysUserDetailEntity 用户详细信息
     */
    void updateUserInfo(SysUserEntity sysUserEntity, SysUserDetailEntity sysUserDetailEntity);

    /**
     * 根据用户ID删除用户信息
     *
     * @param userId 用户ID
     */
    void removeByUserId(Long userId);

    /**
     * 根据身份证号查询用户信息
     *
     * @param idCard 身份证号
     * @param userId 不包括的用户ID
     * @return true：存在；false：不存在
     */
    boolean checkUserIdCardExists(String idCard, Long userId);

}
