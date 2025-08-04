package com.xht.system.modules.authority.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.modules.authority.dao.SysUserDeptPostDao;
import com.xht.system.modules.authority.domain.entity.SysUserDeptPostEntity;
import com.xht.system.modules.authority.domain.request.UserBindDeptPostRequest;
import com.xht.system.modules.authority.service.IUserDeptService;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    @Transactional(rollbackFor = Exception.class)
    public Boolean userBindDept(UserBindDeptPostRequest bindRequest) {
        ThrowUtils.notNull(bindRequest, "绑定请求参数不能为空");
        // 检查是否已存在绑定关系
        boolean existsUserDept = sysUserDeptPostDao.existsUserDept(bindRequest.getUserId(), bindRequest.getDeptId(), bindRequest.getPostId());
        if (existsUserDept) {
            return Boolean.TRUE;
        }
        // 检查部门岗位是否存在
        SysDeptPostEntity deptPostEntity = sysDeptPostDao.findPostByDeptIdAndPostId(bindRequest.getDeptId(), bindRequest.getPostId());
        ThrowUtils.throwIf(Objects.isNull(deptPostEntity), BusinessErrorCode.DATA_NOT_EXIST, "部门岗位不存在");
        // 检查部门是否存在
        Boolean deptExists = sysDeptDao.exists(SysDeptEntity::getId, bindRequest.getDeptId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        // 检查用户是否存在
        boolean userExists = sysUserDao.exists(SysUserEntity::getId, bindRequest.getUserId());
        ThrowUtils.throwIf(!userExists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        // 删除用户原有的部门岗位绑定关系（注意：此处可能误删其他有效绑定）
        sysUserDeptPostDao.removeByUserId(bindRequest.getUserId());
        // 创建新的绑定关系
        SysUserDeptPostEntity entity = new SysUserDeptPostEntity();
        entity.setUserId(bindRequest.getUserId());
        entity.setDeptId(bindRequest.getDeptId());
        entity.setPostId(bindRequest.getPostId());
        entity.setPositionNature(bindRequest.getPositionNature());
        // 保存新的绑定关系
        return sysUserDeptPostDao.save(entity);
    }


    /**
     * 获取已绑定的用户信息
     *
     * @param deptId 部门ID，可选参数，用于筛选指定部门下的已绑定用户
     * @return 返回部门下已绑定的用户信息列表，封装在R对象中
     */
    @Override
    public List<UserSimpleVo> getBindUserByDeptId(Long deptId) {
        return sysUserDeptPostDao.findUserSimpleVoByDeptId(deptId);
    }

}
