package com.xht.system.modules.dept.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.request.SysDeptQueryTreeRequest;

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
     * @return true：成功；false：失败
     */
    Boolean saveDeptInitPost(SysDeptEntity entity);

    /**
     * 更新部门信息
     *
     * @param formRequest     部门更新请求
     * @param oldLeaderUserId 旧的主管用户id
     * @return true：成功；false：失败
     */
    Boolean updateFormRequest(SysDeptEntity formRequest, Long oldLeaderUserId);

    /**
     * 更新部门状态
     *
     * @param id     部门id
     * @param status 部门状态
     * @return true：成功；false：失败
     */
    Boolean updateStatus(Long id, DeptStatusEnums status);

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
     * @param queryRequest 查询请求参数
     * @return 部门列表
     */
    List<SysDeptEntity> queryListRequest(SysDeptQueryTreeRequest queryRequest);

    /**
     * 更新部门主管岗位id
     *
     * @param deptId       部门id
     * @param leaderPostId 主管岗位id
     */
    void updateLeaderPostId(Long deptId, Long leaderPostId);
}