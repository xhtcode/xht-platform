package com.xht.system.modules.user.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.user.dao.SysUserAdminDao;
import com.xht.system.modules.user.dao.mapper.SysUserAdminMapper;
import com.xht.system.modules.user.domain.entity.SysUserAdminEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 管理员用户信息
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysUserAdminDaoImpl extends MapperRepositoryImpl<SysUserAdminMapper, SysUserAdminEntity> implements SysUserAdminDao {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户id
     */
    @Override
    public Optional<SysUserAdminEntity> getUserInfoByUserId(Long userId) {
        return findOneOptional(SysUserAdminEntity::getUserId, userId);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysUserAdminEntity, ?> getFieldId() {
        return SysUserAdminEntity::getId;
    }

}
