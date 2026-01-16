package com.xht.modules.admin.system.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.system.enums.RoleStatusEnums;
import com.xht.modules.admin.system.domain.form.SysRoleForm;
import com.xht.modules.admin.system.domain.query.SysRoleQuery;
import com.xht.modules.admin.system.domain.response.SysRoleResponse;

import java.util.List;

/**
 * 角色 Service接口
 *
 * @author xht
 */
public interface ISysRoleService {

    /**
     * 创建角色
     *
     * @param form 角色表单请求参数
     */
    void create(SysRoleForm form);

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     */
    void removeById(Long id);

    /**
     * 批量删除角色
     *
     * @param ids 角色id
     */
    void removeByIds(List<Long> ids);

    /**
     * 根据ID更新角色
     *
     * @param form 角色更新请求参数
     */
    void updateById(SysRoleForm form);

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     */
    void updateStatus(Long id, RoleStatusEnums status);

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    SysRoleResponse findById(Long id);

    /**
     * 分页查询角色
     *
     * @param query 角色查询请求参数
     * @return 角色分页信息
     */
    PageResponse<SysRoleResponse> findPageList(SysRoleQuery query);

    /***
     * 查询所有角色
     * @return 角色列表
     */
    List<SysRoleResponse> list();

}
