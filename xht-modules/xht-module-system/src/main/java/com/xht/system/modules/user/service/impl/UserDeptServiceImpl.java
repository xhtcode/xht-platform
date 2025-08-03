package com.xht.system.modules.user.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.SysUserDeptPostDao;
import com.xht.system.modules.user.domain.entity.SysUserDeptPostEntity;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.request.UserBindDeptPostRequest;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
import com.xht.system.modules.user.service.IUserDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private final SysDeptPostDao sysDeptPostDao;

    private final SysUserDeptPostDao sysUserDeptPostDao;


    /**
     * 用户绑定部门岗位信息
     *
     * @param bindRequest 用户绑定部门岗位请求参数
     * @return true成功，false失败
     */
    @Override
    public Boolean userBindDept(UserBindDeptPostRequest bindRequest) {
        ThrowUtils.notNull(bindRequest, "绑定请求参数不能为空");
        boolean existsUserDept = sysUserDeptPostDao.existsUserDept(bindRequest.getUserId(), bindRequest.getDeptId(), bindRequest.getPostId());
        if (existsUserDept) {
            return Boolean.TRUE;
        }
        Boolean existsDeptPost = sysDeptPostDao.existsDeptPost(bindRequest.getDeptId(), bindRequest.getPostId());
        ThrowUtils.throwIf(!existsDeptPost, BusinessErrorCode.DATA_NOT_EXIST, "部门岗位不存在");
        Boolean deptExists = sysDeptDao.exists(SysDeptEntity::getId, bindRequest.getDeptId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        boolean userExists = sysUserDao.exists(SysUserEntity::getId, bindRequest.getUserId());
        ThrowUtils.throwIf(!userExists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        SysUserDeptPostEntity entity = new SysUserDeptPostEntity();
        entity.setUserId(bindRequest.getUserId());
        entity.setDeptId(bindRequest.getDeptId());
        entity.setPostId(bindRequest.getPostId());
        entity.setPositionNature(bindRequest.getPositionNature());
        return sysUserDeptPostDao.saveTransactional(entity);
    }

    /**
     * 根据部门ID获取已绑定用户ID列表
     *
     * @param deptId 部门ID
     * @return 已绑定用户ID列表
     */
    @Override
    public List<UserSimpleVo> getBindUserByDeptId(Long deptId) {
        return sysUserDeptPostDao.findUserSimpleVoByDeptId(deptId);
    }

}
