package com.xht.modules.admin.system.service;

import com.xht.framework.core.utils.tree.INode;
import com.xht.modules.admin.system.enums.MenuStatusEnums;
import com.xht.modules.admin.system.domain.form.SysMenuForm;
import com.xht.modules.admin.system.domain.query.SysMenuQuery;
import com.xht.modules.admin.system.domain.response.SysMenuResponse;

import java.util.List;

/**
 * 菜单管理Service接口
 *
 * @author xht
 **/
public interface ISysMenuService {


    /**
     * 创建菜单
     *
     * @param form 菜单表单请求参数
     */
    void create(SysMenuForm form);

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     */
    void removeById(Long id);

    /**
     * 根据ID更新菜单
     *
     * @param form 菜单更新请求参数
     */
    void updateById(SysMenuForm form);

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     */
    void updateStatus(Long id, MenuStatusEnums status);

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    SysMenuResponse findById(Long id);

    /**
     * 查询菜单列表(树形结构)
     *
     * @param query 菜单查询请求参数
     * @return 菜单分页信息
     */
    List<INode<Long>> findTree(SysMenuQuery query);

    /**
     * 根据条件查询是否包含菜单类型为button菜单列表(树形结构)
     *
     * @return 菜单树形结构信息
     */
    List<INode<Long>> getMenuTreeSystemTool();

}
