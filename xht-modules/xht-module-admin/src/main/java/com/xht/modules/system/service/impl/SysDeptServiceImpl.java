package com.xht.modules.system.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.utils.tree.TreeNode;
import com.xht.framework.core.utils.tree.TreeUtils;
import com.xht.api.system.enums.DeptStatusEnums;
import com.xht.modules.system.converter.SysDeptConverter;
import com.xht.modules.system.dao.SysDeptDao;
import com.xht.modules.system.entity.SysDeptEntity;
import com.xht.api.system.domain.form.SysDeptForm;
import com.xht.api.system.domain.query.SysDeptTreeQuery;
import com.xht.api.system.domain.response.SysDeptResponse;
import com.xht.modules.system.service.ISysDeptService;
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

    private final SysDeptConverter sysDeptConverter;

    /**
     * 创建部门
     *
     * @param form 部门表单请求参数
     */
    @Override
    public void create(SysDeptForm form) {
        String deptCode = form.getDeptCode();
        SysDeptEntity parentDept = sysDeptDao.getDefaultParentDeptByParentId(form.getParentId());
        ThrowUtils.throwIf(Objects.isNull(parentDept), BusinessErrorCode.DATA_NOT_EXIST, "父部门不存在");
        Boolean deptCodeUnique = sysDeptDao.checkDeptCodeUnique(null, deptCode);
        ThrowUtils.throwIf(deptCodeUnique, BusinessErrorCode.DATA_EXIST, "部门编码已存在");
        SysDeptEntity entity = sysDeptConverter.toEntity(form);
        entity.setDeptLevel(parentDept.getDeptLevel() + 1);
        entity.setAncestors(parentDept.getAncestors() + "," + parentDept.getId());
        sysDeptDao.saveDeptInitPost(entity);
    }

    /**
     * 根据ID删除部门
     *
     * @param id 部门ID
     */
    @Override
    public void removeById(Long id) {
        Boolean existsDeptPost = sysDeptDao.exists(SysDeptEntity::getId, id);
        ThrowUtils.throwIf(!existsDeptPost, BusinessErrorCode.DATA_NOT_EXIST, "该部门下已有部门，不能删除");
        sysDeptDao.removeById(id);
    }

    /**
     * 根据ID更新部门
     *
     * @param form 部门更新请求参数
     */
    @Override
    public void updateById(SysDeptForm form) {
        // 1.校验部门是否存在
        SysDeptEntity dbDept = sysDeptDao.findById(form.getId());
        ThrowUtils.throwIf(Objects.isNull(dbDept), BusinessErrorCode.DATA_NOT_EXIST, "修改的部门不存在");
        // 2.校验部门编码是否唯一
        String deptCode = form.getDeptCode();
        ThrowUtils.throwIf(sysDeptDao.checkDeptCodeUnique(form.getId(), deptCode), BusinessErrorCode.DATA_EXIST, "部门编码已存在");
        // 3.校验上级部门是否存在
        SysDeptEntity parentDept = sysDeptDao.getDefaultParentDeptByParentId(form.getParentId());
        ThrowUtils.throwIf(Objects.isNull(parentDept), BusinessErrorCode.DATA_NOT_EXIST, "父部门不存在");
        // 转换部门实体
        SysDeptEntity entity = sysDeptConverter.toEntity(form);
        entity.setId(form.getId());
        entity.setDeptLevel(parentDept.getDeptLevel() + 1);
        entity.setAncestors(parentDept.getAncestors() + "," + parentDept.getId());
        sysDeptDao.updateFormRequest(entity);
    }

    /**
     * 更新部门状态
     *
     * @param id     部门ID
     * @param status 部门状态
     */
    @Override
    public void updateStatus(Long id, DeptStatusEnums status) {
        Boolean exists = sysDeptDao.exists(SysDeptEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        sysDeptDao.updateStatus(id, status);
    }

    /**
     * 根据ID查询部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    @Override
    public SysDeptResponse findById(Long id) {
        return sysDeptConverter.toResponse(sysDeptDao.findById(id));
    }

    /**
     * 获取部门树形结构
     *
     * @param query 部门树形结构请求参数
     * @return 部门树形结构
     */
    @Override
    public List<INode<Long>> getDeptTree(SysDeptTreeQuery query) {
        List<SysDeptEntity> list = sysDeptDao.queryListRequest(query);
        List<INode<Long>> treeNodeList = new ArrayList<>();
        for (SysDeptEntity entity : list) {
            TreeNode<Long> node = new TreeNode<>(entity.getId(), entity.getParentId(), entity.getDeptSort());
            node.setExtra(sysDeptConverter.toMap(entity));
            treeNodeList.add(node);
        }
        return TreeUtils.buildList(treeNodeList, false);
    }

}