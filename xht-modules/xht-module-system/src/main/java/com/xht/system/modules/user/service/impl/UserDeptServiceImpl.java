package com.xht.system.modules.user.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
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

import java.util.*;

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

    private final SysUserDeptPostDao sysUserDeptPostDao;


    /**
     * 用户绑定部门岗位信息
     *
     * @param bindRequest 用户绑定部门岗位请求参数
     * @return true成功，false失败
     */
    @Override
    public Boolean userBindDept(List<UserBindDeptPostRequest> bindRequest) {
        ThrowUtils.notEmpty(bindRequest, "绑定请求参数不能为空");
        List<SysUserDeptPostEntity> userDeptEntities = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        List<Long> deptId = new ArrayList<>();
        List<Long> postId = new ArrayList<>();
        Set<String> duplicateRemovalSet = new HashSet<>();
        for (UserBindDeptPostRequest item : bindRequest) {
            userIds.add(item.getUserId());
            deptId.add(item.getDeptId());
            postId.add(item.getPostId());
            SysUserDeptPostEntity entity = new SysUserDeptPostEntity();
            entity.setUserId(item.getUserId());
            entity.setDeptId(item.getDeptId());
            entity.setPostId(item.getPostId());
            userDeptEntities.add(entity);
            duplicateRemovalSet.add(item.getUserId() + "_" + item.getDeptId() + "_" + item.getPostId());
        }
        ThrowUtils.throwIf(duplicateRemovalSet.size() != bindRequest.size(), BusinessErrorCode.DATA_EXIST, "存在重复绑定数据");
        Boolean deptExists = sysDeptDao.existsIn(SysDeptEntity::getId, deptId);
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        Boolean postExists = sysUserDeptPostDao.existsIn(SysUserDeptPostEntity::getUserId, postId);
        ThrowUtils.throwIf(!postExists, BusinessErrorCode.DATA_NOT_EXIST, "部门岗位不存在");
        boolean userExists = sysUserDao.existsIn(SysUserEntity::getId, userIds);
        ThrowUtils.throwIf(!userExists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        return sysUserDeptPostDao.saveAll(userDeptEntities);
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
        return Optional.ofNullable(sysUserDeptPostDao.getDeptPostByUserId(userId)).orElse(new SysDeptPostVo());
    }
}
