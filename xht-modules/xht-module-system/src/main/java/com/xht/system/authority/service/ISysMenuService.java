package com.xht.system.authority.service;

import com.xht.framework.core.utils.tree.INode;
import com.xht.system.authority.common.enums.MenuStatusEnums;
import com.xht.system.authority.common.enums.MenuTypeEnums;
import com.xht.system.authority.domain.request.SysMenuFormRequest;
import com.xht.system.authority.domain.request.SysMenuQueryRequest;
import com.xht.system.authority.domain.response.SysMenuResponse;

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
     * @param formRequest 菜单表单请求参数
     * @return 操作结果
     */
    Boolean create(SysMenuFormRequest formRequest);

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新菜单
     *
     * @param formRequest 菜单更新请求参数
     * @return 操作结果
     */
    Boolean updateById(SysMenuFormRequest formRequest);

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     * @return 操作结果
     */
    Boolean updateStatus(Long id, MenuStatusEnums status);

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    SysMenuResponse getById(Long id);

    /**
     * 查询菜单列表(树形结构)
     *
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    List<INode<Long>> findTree(SysMenuQueryRequest queryRequest);

    /**
     * 根据菜单类型查询菜单列表(树形结构)
     *
     * @param menuType 菜单类型
     * @return 菜单树形结构信息
     */
    List<INode<Long>> findSystemTree(MenuTypeEnums menuType);

}
