package com.xht.modules.admin.system.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.api.system.enums.DeptStatusEnums;
import com.xht.modules.admin.system.entity.SysDeptEntity;
import com.xht.api.system.domain.query.SysDeptTreeQuery;

import java.util.List;

/**
 * 部门管理Dao
 *
 * @author xht
 */
public interface SysDeptDao extends MapperRepository<SysDeptEntity> {

    /**
     * 保存部门初始化数据
     *
     * @param entity 部门实体
     */
    void saveDeptInitPost(SysDeptEntity entity);

    /**
     * 更新部门信息
     *
     * @param form 部门更新请求
     */
    void updateFormRequest(SysDeptEntity form);

    /**
     * 更新部门状态
     *
     * @param id     部门id
     * @param status 部门状态
     */
    void updateStatus(Long id, DeptStatusEnums status);

    /**
     * 检查部门编码是否唯一
     *
     * @param deptId   部门id
     * @param deptCode 部门编码
     * @return true：唯一；false：不唯一
     */
    Boolean checkDeptCodeUnique(Long deptId, String deptCode);

    /**
     * 获取上级父部门信息
     *
     * @param parentId 父部门id
     * @return 父部门
     */
    SysDeptEntity getDefaultParentDeptByParentId(Long parentId);

    /**
     * 查询部门列表信息
     *
     * @param query 查询请求参数
     * @return 部门列表
     */
    List<SysDeptEntity> queryListRequest(SysDeptTreeQuery query);

}