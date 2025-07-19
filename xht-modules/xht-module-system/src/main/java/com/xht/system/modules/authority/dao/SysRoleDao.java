package com.xht.system.modules.authority.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.mybatis.dao.BasicDao;
import com.xht.system.modules.authority.common.enums.RoleStatusEnums;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.request.SysRoleFormRequest;
import com.xht.system.modules.authority.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 系统角色管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysRoleDao extends BasicDao<SysRoleMapper, SysRoleEntity> {

    /**
     * 更新角色信息
     *
     * @param formRequest 角色信息
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(SysRoleFormRequest formRequest) {
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysRoleEntity::getRoleCode, formRequest.getRoleCode());
        updateWrapper.set(SysRoleEntity::getRoleName, formRequest.getRoleName());
        updateWrapper.set(SysRoleEntity::getDataScope, formRequest.getDataScope());
        updateWrapper.set(SysRoleEntity::getRoleStatus, formRequest.getRoleStatus());
        updateWrapper.set(SysRoleEntity::getRoleSort, formRequest.getRoleSort());
        updateWrapper.set(SysRoleEntity::getRemark, formRequest.getRemark());
        updateWrapper.eq(SysRoleEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(Long id, RoleStatusEnums status) {
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysRoleEntity::getRoleStatus, status);
        updateWrapper.eq(SysRoleEntity::getId, id);
        return update(updateWrapper);
    }

    /**
     * 角色编码是否存在
     *
     * @param roleId   角色ID
     * @param roleCode 角色编码
     * @return 是否存在
     */
    public Boolean existsRoleCode(Long roleId, String roleCode) {
        LambdaQueryWrapper<SysRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoleEntity::getRoleCode, roleCode)
                .ne(Objects.nonNull(roleId), SysRoleEntity::getId, roleId);
        return dataExists(count(lambdaQueryWrapper));
    }
}
