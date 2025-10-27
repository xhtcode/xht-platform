package com.xht.system.modules.authority.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.utils.tree.TreeNode;
import com.xht.framework.core.utils.tree.TreeUtils;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import com.xht.system.modules.authority.converter.SysMenuConverter;
import com.xht.system.modules.authority.dao.SysMenuDao;
import com.xht.system.modules.authority.domain.entity.SysMenuEntity;
import com.xht.system.modules.authority.domain.form.SysMenuBasicForm;
import com.xht.system.modules.authority.domain.query.SysMenuBasicQuery;
import com.xht.system.modules.authority.domain.response.SysMenuResponse;
import com.xht.system.modules.authority.service.ISysMenuService;
import com.xht.system.modules.authority.utils.MenuValidationFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xht.system.modules.authority.common.constant.MenuConstant.DEFAULT_PARENT_ID;

/**
 * 菜单管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements ISysMenuService {

    private final SysMenuDao sysMenuDao;

    private final SysMenuConverter sysMenuConverter;

    /**
     * 创建菜单
     *
     * @param form 菜单表单请求参数
     */
    @Override
    public void create(SysMenuBasicForm form) {
        MenuValidationFormat.validationFormat(form);
        checkExitsParentMenu(form);
        SysMenuEntity entity = sysMenuConverter.toEntity(form);
        sysMenuDao.saveTransactional(entity);
    }

    /**
     * 检查上级菜单是否存在
     *
     * @param form 菜单表单请求参数
     */
    private void checkExitsParentMenu(SysMenuBasicForm form) {
        if (Objects.equals(DEFAULT_PARENT_ID, form.getParentId())) {
            MenuValidationFormat.checkParentType(MenuTypeEnums.M, form.getMenuType());
        } else {
            MenuTypeEnums parentMenuType = sysMenuDao.getMenuType(form.getParentId());
            ThrowUtils.throwIf(Objects.isNull(parentMenuType), BusinessErrorCode.DATA_NOT_EXIST, "上级菜单不存在");
            MenuValidationFormat.checkParentType(parentMenuType, form.getMenuType());
        }
    }

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     */
    @Override
    public void removeById(Long id) {
        Boolean exists = sysMenuDao.exists(SysMenuEntity::getParentId, id);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "菜单下存在子菜单，不能删除");
        sysMenuDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新菜单
     *
     * @param form 菜单更新请求参数
     */
    @Override
    public void updateById(SysMenuBasicForm form) {
        MenuValidationFormat.validationFormat(form);
        checkExitsParentMenu(form);
        Boolean menuExists = sysMenuDao.exists(SysMenuEntity::getId, form.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
        sysMenuDao.updateFormRequest(form);
    }

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     */
    @Override
    public void updateStatus(Long id, MenuStatusEnums status) {
        Boolean exists = sysMenuDao.exists(SysMenuEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
        sysMenuDao.updateStatus(id, status);
    }

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenuResponse findById(Long id) {
        return sysMenuConverter.toResponse(sysMenuDao.findById(id));
    }

    /**
     * 查询菜单列表(树形结构)
     *
     * @param query 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public List<INode<Long>> findTree(SysMenuBasicQuery query) {
        List<SysMenuEntity> list = sysMenuDao.getMenuList(query);
        List<INode<Long>> treeNodeList = new ArrayList<>();
        for (SysMenuEntity entity : list) {
            TreeNode<Long> node = new TreeNode<>(entity.getId(), entity.getParentId(), entity.getMenuSort());
            node.setExtra(sysMenuConverter.toMap(entity, true));
            treeNodeList.add(node);
        }
        return TreeUtils.buildList(treeNodeList, false);
    }

    /**
     * 根据菜单类型查询菜单列表(树形结构)
     *
     * @param menuType 菜单类型
     * @return 菜单树形结构信息
     */
    @Override
    public List<INode<Long>> findSystemTree(MenuTypeEnums menuType) {
        List<SysMenuEntity> list = sysMenuDao.listMenuTree(menuType);
        List<INode<Long>> treeNodeList = new ArrayList<>();
        for (SysMenuEntity entity : list) {
            TreeNode<Long> node = new TreeNode<>(entity.getId(), entity.getParentId(), entity.getMenuSort());
            node.setExtra(sysMenuConverter.toMap(entity, false));
            treeNodeList.add(node);
        }
        return TreeUtils.buildList(treeNodeList, false);
    }


}
