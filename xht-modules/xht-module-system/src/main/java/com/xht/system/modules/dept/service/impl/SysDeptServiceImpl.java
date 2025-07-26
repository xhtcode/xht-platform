package com.xht.system.modules.dept.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.utils.tree.TreeNode;
import com.xht.framework.core.utils.tree.TreeUtils;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import com.xht.system.modules.dept.converter.SysDeptConverter;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.request.SysDeptFormRequest;
import com.xht.system.modules.dept.domain.request.SysDeptQueryTreeRequest;
import com.xht.system.modules.dept.domain.response.SysDeptResponse;
import com.xht.system.modules.dept.service.ISysDeptService;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 部门管理Service实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl implements ISysDeptService {

    private final SysDeptDao sysDeptDao;

    private final SysDeptPostDao sysDeptPostDao;

    private final SysUserDao sysUserDao;

    private final SysDeptConverter sysDeptConverter;

    /**
     * 创建部门
     *
     * @param formRequest 部门表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(SysDeptFormRequest formRequest) {
        String deptCode = formRequest.getDeptCode();
        SysDeptEntity parentDept = sysDeptDao.getDefaultParentDeptByParentId(formRequest.getParentId());
        ThrowUtils.throwIf(Objects.isNull(parentDept), BusinessErrorCode.DATA_NOT_EXIST, "父部门不存在");
        Boolean deptCodeUnique = sysDeptDao.checkDeptCodeUnique(null, deptCode);
        ThrowUtils.throwIf(deptCodeUnique, BusinessErrorCode.DATA_EXIST, "部门编码已存在");
        SysDeptEntity entity = sysDeptConverter.toEntity(formRequest);
        entity.setDeptLevel(parentDept.getDeptLevel() + 1);
        entity.setAncestors(parentDept.getAncestors() + "," + parentDept.getId());
        Long leaderUserId = formRequest.getLeaderUserId();
        if (Objects.nonNull(leaderUserId)) {
            entity.setLeaderUserId(leaderUserId);
            SysUserEntity userEntity = sysUserDao.findById(leaderUserId);
            ThrowUtils.throwIf(Objects.isNull(userEntity), UserErrorCode.DATA_NOT_EXIST, "未查找到用户信息");
            ThrowUtils.throwIf(!Objects.equals(UserStatusEnums.NORMAL, userEntity.getUserStatus()), UserErrorCode.DATA_NOT_EXIST, "用户状态异常");
            entity.setLeaderName(userEntity.getUserName());
        }
        return sysDeptDao.saveDeptInitPost(entity);
    }

    /**
     * 根据ID删除部门
     *
     * @param id 部门ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        Boolean existsDeptPost = sysDeptPostDao.existsDeptPost(id);
        ThrowUtils.throwIf(existsDeptPost, BusinessErrorCode.DATA_NOT_EXIST, "该部门下已有岗位，不能删除");
        return sysDeptDao.deleteById(id);
    }

    /**
     * 根据ID更新部门
     *
     * @param formRequest 部门更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(SysDeptFormRequest formRequest) {
        // 1.校验部门是否存在
        SysDeptEntity dbDept = sysDeptDao.findById(formRequest.getId());
        ThrowUtils.throwIf(Objects.isNull(dbDept), BusinessErrorCode.DATA_NOT_EXIST, "修改的部门不存在");
        // 2.校验部门编码是否唯一
        String deptCode = formRequest.getDeptCode();
        ThrowUtils.throwIf(sysDeptDao.checkDeptCodeUnique(formRequest.getId(), deptCode), BusinessErrorCode.DATA_EXIST, "部门编码已存在");
        // 3.校验上级部门是否存在
        SysDeptEntity parentDept = sysDeptDao.getDefaultParentDeptByParentId(formRequest.getParentId());
        ThrowUtils.throwIf(Objects.isNull(parentDept), BusinessErrorCode.DATA_NOT_EXIST, "父部门不存在");
        // 4.校验部门主管是否修改
        Long leaderUserId = dbDept.getLeaderUserId();
        Long updateLeaderUserId = formRequest.getLeaderUserId();
        String leaderName = dbDept.getLeaderName();
        if (!Objects.equals(leaderUserId, updateLeaderUserId)) {
            //校验修改的部门主管用户是否存在
            SysUserEntity userEntity = sysUserDao.findById(formRequest.getLeaderUserId());
            ThrowUtils.throwIf(Objects.isNull(userEntity), UserErrorCode.DATA_NOT_EXIST, "未查找到用户信息");
            ThrowUtils.throwIf(!Objects.equals(UserStatusEnums.NORMAL, userEntity.getUserStatus()), UserErrorCode.DATA_NOT_EXIST, "用户状态异常");
            leaderName = userEntity.getUserName();
        }
        // 转换部门实体
        SysDeptEntity entity = sysDeptConverter.toEntity(formRequest);
        entity.setId(formRequest.getId());
        entity.setDeptLevel(parentDept.getDeptLevel() + 1);
        entity.setAncestors(parentDept.getAncestors() + "," + parentDept.getId());
        entity.setLeaderName(leaderName);
        return sysDeptDao.updateFormRequest(entity, leaderUserId);
    }

    /**
     * 更新部门状态
     *
     * @param id     部门ID
     * @param status 部门状态
     * @return 操作结果
     */
    @Override
    public Boolean updateStatus(Long id, DeptStatusEnums status) {
        Boolean exists = sysDeptDao.exists(SysDeptEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        return sysDeptDao.updateStatus(id, status);
    }

    /**
     * 根据ID查询部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    @Override
    public SysDeptResponse getById(Long id) {
        return sysDeptConverter.toResponse(sysDeptDao.findById(id));
    }

    /**
     * 获取部门树形结构
     *
     * @param queryRequest 部门树形结构请求参数
     * @return 部门树形结构
     */
    @Override
    public List<INode<Long>> getDeptTree(SysDeptQueryTreeRequest queryRequest) {
        List<SysDeptEntity> list = sysDeptDao.queryListRequest(queryRequest);
        List<INode<Long>> treeNodeList = new ArrayList<>();
        for (SysDeptEntity entity : list) {
            TreeNode<Long> node = new TreeNode<>(entity.getId(), entity.getParentId(), entity.getDeptSort());
            node.setExtra(sysDeptConverter.toMap(entity));
            treeNodeList.add(node);
        }
        return TreeUtils.buildList(treeNodeList, false);
    }

}