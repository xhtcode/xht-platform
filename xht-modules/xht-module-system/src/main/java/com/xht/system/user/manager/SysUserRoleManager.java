package com.xht.system.user.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.authority.common.enums.RoleStatusEnums;
import com.xht.system.user.domain.entity.SysUserRoleEntity;
import com.xht.system.user.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户角色
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysUserRoleManager extends BasicManager<SysUserRoleMapper, SysUserRoleEntity> {

    /**
     * 保存用户角色
     *
     * @param userId              用户ID
     * @param sysUserRoleEntities 角色列表
     * @return Boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUserRole(Long userId, List<SysUserRoleEntity> sysUserRoleEntities) {
        LambdaQueryWrapper<SysUserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRoleEntity::getUserId, userId);
        remove(queryWrapper);
        if (CollectionUtils.isEmpty(sysUserRoleEntities)) {
            return true;
        }
        return saveBatch(sysUserRoleEntities);
    }

    /**
     * 根据用户ID获取角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    public List<String> getRoleCodes(Long userId) {
        return getBaseMapper().getRoleCodes(userId);
    }

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    public List<Long> getRoleId(String userId) {
        return this.getBaseMapper().selectRoleIdByUserId(RoleStatusEnums.NORMAL, userId);
    }
}
