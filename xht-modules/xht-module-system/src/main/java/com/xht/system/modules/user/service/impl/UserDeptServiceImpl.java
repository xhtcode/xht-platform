package com.xht.system.modules.user.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.SysUserDeptDao;
import com.xht.system.modules.user.domain.entity.SysUserDeptEntity;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
import com.xht.system.modules.user.service.IUserDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 部门用户service实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeptServiceImpl implements IUserDeptService {

    private final SysUserDao sysUserDao;

    private final SysDeptDao sysDeptDao;

    private final SysUserDeptDao sysUserDeptDao;


    /**
     * 部门分配用户
     *
     * @param deptId  部门ID
     * @param userIds 用户ID列表
     * @return 成功返回true，失败返回false
     */
    @Override
    public Boolean userBindDept(Long deptId, List<Long> userIds) {
        Boolean deptExists = sysDeptDao.exists(SysDeptEntity::getId, deptId);
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        List<SysUserDeptEntity> userDeptEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userIds)) {
            boolean userExists = sysUserDao.existsByUserId(userIds);
            ThrowUtils.throwIf(!userExists, UserErrorCode.DATA_NOT_EXIST, "所选用户不存在");
            userIds.forEach(item -> {
                SysUserDeptEntity entity = new SysUserDeptEntity();
                entity.setUserId(item);
                entity.setDeptId(deptId);
                userDeptEntities.add(entity);
            });
        }
        return sysUserDeptDao.saveUserDept(deptId, userDeptEntities);
    }

    /**
     * 根据部门ID获取已绑定用户ID列表
     *
     * @param deptId 部门ID
     * @return 已绑定用户ID列表
     */
    @Override
    public List<UserSimpleVo> getBindUserByDeptId(Long deptId) {
        return sysUserDeptDao.findUserSimpleVoByDeptId(deptId);
    }

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    @Override
    public SysDeptPostVo getDeptByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        return Optional.ofNullable(sysUserDeptDao.getDeptPostByUserId(userId)).orElse(new SysDeptPostVo());
    }
}
