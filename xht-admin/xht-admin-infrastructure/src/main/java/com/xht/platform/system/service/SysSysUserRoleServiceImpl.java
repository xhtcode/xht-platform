package com.xht.platform.system.service;

import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.exception.code.UserErrorCode;
import com.xht.framework.utils.CollectionUtils;
import com.xht.framework.utils.ThrowUtils;
import com.xht.platform.system.converter.SysRoleConverter;
import com.xht.platform.system.dao.SysRoleDao;
import com.xht.platform.system.dao.SysUserDao;
import com.xht.platform.system.dao.SysUserRoleDao;
import com.xht.platform.system.domain.vo.SysUserRoleBindVo;
import com.xht.platform.system.entity.SysUserEntity;
import com.xht.platform.system.entity.SysUserRoleEntity;
import com.xht.platform.system.enums.RoleStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class SysSysUserRoleServiceImpl implements ISysUserRoleService {

    private final SysUserDao sysUserDao;

    private final SysRoleDao sysRoleDao;

    private final SysRoleConverter sysRoleConverter;

    private final SysUserRoleDao sysUserRoleDao;


    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId  用户ID
     * @param roleIds 角色ID
     */
    @Override
    public void userBindRole(Long userId, List<Long> roleIds) {
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
        sysUserRoleDao.saveUserRole(userId, sysUserRoleEntities);
    }

    /**
     * 获取当前用户拥有的角色ID列表
     *
     * @param userId 用户ID
     * @return 用户角色绑定信息VO
     */
    @Override
    public SysUserRoleBindVo findBindRoleIds(Long userId) {
        ThrowUtils.notNull(userId, "用户ID不能为空");
        SysUserEntity sysUser = sysUserDao.findById(userId);
        ThrowUtils.notNull(sysUser, "用户不存在");
        SysUserRoleBindVo result = new SysUserRoleBindVo();
        result.setUserId(sysUser.getId());
        result.setUserType(sysUser.getUserType());
        result.setUserName(sysUser.getUserName());
        result.setNickName(sysUser.getNickName());
        result.setUserStatus(sysUser.getUserStatus());
        result.setBindRoleIds(sysUserRoleDao.findBindRoleIds(userId));
        result.setRoles(sysRoleConverter.toResponse(sysRoleDao.findListByStatus(RoleStatusEnum.NORMAL)));
        return result;
    }
}
