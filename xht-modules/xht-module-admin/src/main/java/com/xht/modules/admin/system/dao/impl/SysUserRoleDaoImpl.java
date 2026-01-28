package com.xht.modules.admin.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.system.dao.SysUserRoleDao;
import com.xht.modules.admin.system.dao.mapper.SysUserRoleMapper;
import com.xht.modules.admin.system.entity.SysRoleEntity;
import com.xht.modules.admin.system.entity.SysUserRoleEntity;
import com.xht.modules.admin.system.enums.RoleStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户角色
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysUserRoleDaoImpl extends MapperRepositoryImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleDao {

    /**
     * 保存用户角色
     *
     * @param userId              用户ID
     * @param sysUserRoleEntities 角色列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRole(Long userId, List<SysUserRoleEntity> sysUserRoleEntities) {
        LambdaQueryWrapper<SysUserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRoleEntity::getUserId, userId);
        remove(queryWrapper);
        if (CollectionUtils.isEmpty(sysUserRoleEntities)) {
            return;
        }
        saveBatch(sysUserRoleEntities, 500);
    }

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @Override
    public List<Long> getRoleId(String userId) {
        return baseMapper.selectRoleIdByUserId(RoleStatusEnums.NORMAL, userId);
    }

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRoleEntity> findRoleListByUserId(Long userId) {
        return baseMapper.findRoleListByUserId(userId);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysUserRoleEntity, ?> getFieldId() {
        return SysUserRoleEntity::getUserId;
    }
}
