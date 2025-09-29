package com.xht.system.modules.authority.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.modules.authority.common.enums.RoleStatusEnums;
import com.xht.system.modules.authority.domain.request.SysRoleFormRequest;
import com.xht.system.modules.authority.domain.request.SysRoleQueryRequest;
import com.xht.system.modules.authority.domain.response.SysRoleResponse;

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
     * @param formRequest 角色表单请求参数
     * @return 操作结果
     */
    Boolean create(SysRoleFormRequest formRequest);

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 批量删除角色
     *
     * @param ids 角色id
     * @return 操作结果
     */
    Boolean removeByIds(List<Long> ids);

    /**
     * 根据ID更新角色
     *
     * @param formRequest 角色更新请求参数
     * @return 操作结果
     */
    Boolean updateById(SysRoleFormRequest formRequest);

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     * @return 操作结果
     */
    Boolean updateStatus(Long id, RoleStatusEnums status);

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
     * @param queryRequest 角色查询请求参数
     * @return 角色分页信息
     */
    PageResponse<SysRoleResponse> pageList(SysRoleQueryRequest queryRequest);

    /***
     * 查询所有角色
     * @return 角色列表
     */
    List<SysRoleResponse> list();

}
