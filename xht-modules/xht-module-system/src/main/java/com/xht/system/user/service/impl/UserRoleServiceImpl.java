package com.xht.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.authority.domain.entity.SysRoleEntity;
import com.xht.system.authority.manager.SysRoleManager;
import com.xht.system.user.domain.entity.SysUserEntity;
import com.xht.system.user.domain.entity.SysUserRoleEntity;
import com.xht.system.user.manager.SysUserManager;
import com.xht.system.user.manager.SysUserRoleManager;
import com.xht.system.user.service.IUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色Service实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements IUserRoleService {

    private final SysUserManager sysUserManager;

    private final SysRoleManager sysRoleManager;

    private final SysUserRoleManager sysUserRoleManager;


    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId  用户ID
     * @param roleIds 角色ID
     * @return 成功返回true，失败返回false
     */
    @Override
    public Boolean userBindRole(Long userId, List<Long> roleIds) {
        Boolean userExists = sysUserManager.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(!userExists, UserErrorCode.DATA_NOT_EXIST);
        List<SysUserRoleEntity> sysUserRoleEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleIds)) {
            LambdaQueryWrapper<SysRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysRoleEntity::getId, roleIds);
            boolean roleExists = sysRoleManager.exists(queryWrapper);
            ThrowUtils.throwIf(!roleExists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
            roleIds.forEach(item -> {
                SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                sysUserRoleEntity.setRoleId(item);
                sysUserRoleEntity.setUserId(userId);
                sysUserRoleEntities.add(sysUserRoleEntity);
            });
        }
        return sysUserRoleManager.saveUserRole(userId, sysUserRoleEntities);
    }

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @Override
    public List<Long> selectRoleIdByUserId(String userId) {
        ThrowUtils.hasText(userId, "用户ID不能为空");
        return sysUserRoleManager.getRoleId(userId);
    }
}
