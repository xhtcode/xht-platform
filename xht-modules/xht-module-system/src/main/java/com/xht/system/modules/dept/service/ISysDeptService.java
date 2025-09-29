package com.xht.system.modules.dept.service;

import com.xht.framework.core.utils.tree.INode;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import com.xht.system.modules.dept.domain.request.SysDeptFormRequest;
import com.xht.system.modules.dept.domain.request.SysDeptQueryTreeRequest;
import com.xht.system.modules.dept.domain.response.SysDeptResponse;

import java.util.List;

/**
 * 部门 Service接口
 *
 * @author xht
 */
public interface ISysDeptService {

    /**
     * 创建部门
     *
     * @param formRequest 部门表单请求参数
     * @return 操作结果
     */
    Boolean create(SysDeptFormRequest formRequest);

    /**
     * 根据ID删除部门
     *
     * @param id 部门ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新部门
     *
     * @param formRequest 部门更新请求参数
     * @return 操作结果
     */
    Boolean updateById(SysDeptFormRequest formRequest);

    /**
     * 更新部门状态
     *
     * @param id     部门ID
     * @param status 部门状态
     * @return 操作结果
     */
    Boolean updateStatus(Long id, DeptStatusEnums status);

    /**
     * 根据ID查询部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    SysDeptResponse findById(Long id);

    /**
     * 获取部门树形结构
     *
     * @param treeRequest 部门树形结构请求参数
     * @return 部门树形结构
     */
    List<INode<Long>> getDeptTree(SysDeptQueryTreeRequest treeRequest);
}