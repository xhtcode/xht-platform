package com.xht.system.modules.user.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.modules.authority.dao.SysRoleDao;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.SysUserRoleDao;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.entity.SysUserRoleEntity;
import com.xht.system.modules.user.service.IUserRoleService;
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

    private final SysUserDao sysUserDao;

    private final SysRoleDao sysRoleDao;

    private final SysUserRoleDao sysUserRoleDao;


    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId  用户ID
     * @param roleIds 角色ID
     * @return 成功返回true，失败返回false
     */
    @Override
    public Boolean userBindRole(Long userId, List<Long> roleIds) {
        Boolean userExists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(!userExists, UserErrorCode.DATA_NOT_EXIST);
        List<SysUserRoleEntity> sysUserRoleEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleIds)) {
            boolean roleExists = sysRoleDao.existsByRoleId(roleIds);
            ThrowUtils.throwIf(!roleExists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
            roleIds.forEach(item -> {
                SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                sysUserRoleEntity.setRoleId(item);
                sysUserRoleEntity.setUserId(userId);
                sysUserRoleEntities.add(sysUserRoleEntity);
            });
        }
        return sysUserRoleDao.saveUserRole(userId, sysUserRoleEntities);
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
        return sysUserRoleDao.getRoleId(userId);
    }
}
