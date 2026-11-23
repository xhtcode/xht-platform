package com.xht.platform.system.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.platform.system.domain.form.SysRoleTypeForm;
import com.xht.platform.system.domain.form.SysRoleTypeMenuBindForm;
import com.xht.platform.system.domain.query.SysRoleTypeQuery;
import com.xht.platform.system.domain.response.RoleSelectedMenuResponse;
import com.xht.platform.system.domain.response.SysRoleTypeResponse;
import com.xht.platform.system.enums.RoleStatusEnum;

import java.util.List;

/**
 * 角色类型 Service接口
 *
 * @author xht
 */
public interface ISysRoleTypeService {

    /**
     * 创建角色类型
     *
     * @param form 角色类型表单请求参数
     */
    void create(SysRoleTypeForm form);

    /**
     * 删除角色类型
     *
     * @param id 角色类型id
     */
    void removeById(Long id);

    /**
     * 根据ID更新角色类型
     *
     * @param id   角色类型ID
     * @param form 角色类型更新请求参数
     */
    void updateById(Long id, SysRoleTypeForm form);

    /**
     * 根据ID查询角色类型
     *
     * @param id 角色类型ID
     * @return 角色类型信息
     */
    SysRoleTypeResponse findById(Long id);

    /**
     * 分页查询角色类型
     *
     * @param query 角色类型查询请求参数
     * @return 角色类型分页信息
     */
    PageResponse<SysRoleTypeResponse> findPageList(SysRoleTypeQuery query);

    /**
     * 查询所有角色类型
     *
     * @return 角色类型列表
     */
    List<SysRoleTypeResponse> list();

    /**
     * 角色类型绑定菜单
     *
     * @param bindRequest 角色类型菜单绑定请求
     */
    void roleTypeMenuBind(SysRoleTypeMenuBindForm bindRequest);

    /**
     * 根据角色类型ID获取已选菜单信息
     *
     * @param roleTypeId 角色类型ID
     * @return 已选菜单响应（含全量菜单树 + 已勾选菜单ID）
     */
    RoleSelectedMenuResponse selectMenuIdByTypeId(String roleTypeId);

}
