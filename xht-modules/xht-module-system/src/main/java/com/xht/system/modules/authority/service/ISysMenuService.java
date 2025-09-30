package com.xht.system.modules.authority.service;

import com.xht.framework.core.utils.tree.INode;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import com.xht.system.modules.authority.domain.request.SysMenuForm;
import com.xht.system.modules.authority.domain.request.SysMenuQuery;
import com.xht.system.modules.authority.domain.response.SysMenuResp;

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
    SysMenuResp findById(Long id);

    /**
     * 查询菜单列表(树形结构)
     *
     * @param query 菜单查询请求参数
     * @return 菜单分页信息
     */
    List<INode<Long>> findTree(SysMenuQuery query);

    /**
     * 根据菜单类型查询菜单列表(树形结构)
     *
     * @param menuType 菜单类型
     * @return 菜单树形结构信息
     */
    List<INode<Long>> findSystemTree(MenuTypeEnums menuType);

}
