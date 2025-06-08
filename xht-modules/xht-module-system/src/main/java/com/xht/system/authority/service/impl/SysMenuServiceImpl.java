package com.xht.system.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.utils.tree.TreeNode;
import com.xht.framework.core.utils.tree.TreeUtils;
import com.xht.system.authority.common.enums.MenuStatusEnums;
import com.xht.system.authority.common.enums.MenuTypeEnums;
import com.xht.system.authority.converter.SysMenuConverter;
import com.xht.system.authority.domain.entity.SysMenuEntity;
import com.xht.system.authority.domain.request.SysMenuFormRequest;
import com.xht.system.authority.domain.request.SysMenuQueryRequest;
import com.xht.system.authority.domain.response.SysMenuResponse;
import com.xht.system.authority.manager.SysMenuManager;
import com.xht.system.authority.service.ISysMenuService;
import com.xht.system.authority.utils.MenuValidationFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xht.system.authority.common.constant.MenuConstant.DEFAULT_PARENT_ID;

/**
 * 菜单管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements ISysMenuService {

    private final SysMenuManager sysMenuManager;

    private final SysMenuConverter sysMenuConverter;


    /**
     * 创建菜单
     *
     * @param formRequest 菜单创建请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(SysMenuFormRequest formRequest) {
        MenuValidationFormat.validationFormat(formRequest);
        checkExitsParentMenu(formRequest);
        SysMenuEntity entity = sysMenuConverter.toEntity(formRequest);
        return sysMenuManager.saveTransactional(entity);
    }

    /**
     * 检查上级菜单是否存在
     *
     * @param formRequest 菜单创建请求参数
     */
    private void checkExitsParentMenu(SysMenuFormRequest formRequest) {
        if (Objects.equals(DEFAULT_PARENT_ID, formRequest.getParentId())) {
            MenuValidationFormat.checkParentType(MenuTypeEnums.M, formRequest.getMenuType());
        } else {
            LambdaQueryWrapper<SysMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysMenuEntity::getMenuType);
            queryWrapper.eq(SysMenuEntity::getId, formRequest.getParentId());
            SysMenuEntity parentMenu = sysMenuManager.getById(queryWrapper);
            ThrowUtils.throwIf(Objects.isNull(parentMenu), BusinessErrorCode.DATA_NOT_EXIST, "上级菜单不存在");
            MenuValidationFormat.checkParentType(parentMenu.getMenuType(), formRequest.getMenuType());
        }
    }

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        Boolean exists = sysMenuManager.exists(SysMenuEntity::getParentId, id);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "菜单下存在子菜单，不能删除");
        return sysMenuManager.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新菜单
     *
     * @param formRequest 菜单更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(SysMenuFormRequest formRequest) {
        MenuValidationFormat.validationFormat(formRequest);
        checkExitsParentMenu(formRequest);
        Boolean menuExists = sysMenuManager.exists(SysMenuEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
        return sysMenuManager.formRequest(formRequest);
    }

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     * @return 操作结果
     */
    @Override
    public Boolean updateStatus(Long id, MenuStatusEnums status) {
        Boolean exists = sysMenuManager.exists(SysMenuEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
        return sysMenuManager.updateStatus(id, status);
    }

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenuResponse getById(Long id) {
        return sysMenuConverter.toResponse(sysMenuManager.getById(id));
    }

    /**
     * 查询菜单列表(树形结构)
     *
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public List<INode<Long>> findTree(SysMenuQueryRequest queryRequest) {
        // @formatter:off
        LambdaQueryWrapper<SysMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.and(StringUtils.hasText(queryRequest.getKeyWord()), wrapper ->
                wrapper.like(SysMenuEntity::getMenuName, queryRequest.getKeyWord())
                        .or()
                        .like(SysMenuEntity::getMenuAuthority, queryRequest.getKeyWord())
        );
        lambdaQueryWrapper
                .eq(Objects.nonNull(queryRequest.getParentId()), SysMenuEntity::getParentId, queryRequest.getParentId())
                .eq(Objects.nonNull(queryRequest.getMenuType()), SysMenuEntity::getMenuType, queryRequest.getMenuType())
                .like(StringUtils.hasText(queryRequest.getMenuName()), SysMenuEntity::getMenuName, queryRequest.getMenuName())
                .eq(Objects.nonNull(queryRequest.getMenuStatus()), SysMenuEntity::getMenuStatus, queryRequest.getMenuStatus());
        // @formatter:on
        List<SysMenuEntity> list = sysMenuManager.list(lambdaQueryWrapper);
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
        // @formatter:off
        LambdaQueryWrapper<SysMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(
                SysMenuEntity::getId,
                SysMenuEntity::getParentId,
                SysMenuEntity::getMenuType,
                SysMenuEntity::getMenuName,
                SysMenuEntity::getMenuIcon,
                SysMenuEntity::getMenuSort
        );
        lambdaQueryWrapper
                .ne(!Objects.equals(MenuTypeEnums.ALL,menuType), SysMenuEntity::getMenuType, MenuTypeEnums.B)
                .eq(SysMenuEntity::getMenuStatus,MenuStatusEnums.NORMAL);
        // @formatter:on
        List<SysMenuEntity> list = sysMenuManager.list(lambdaQueryWrapper);
        List<INode<Long>> treeNodeList = new ArrayList<>();
        for (SysMenuEntity entity : list) {
            TreeNode<Long> node = new TreeNode<>(entity.getId(), entity.getParentId(), entity.getMenuSort());
            node.setExtra(sysMenuConverter.toMap(entity, false));
            treeNodeList.add(node);
        }
        return TreeUtils.buildList(treeNodeList, false);
    }


}
