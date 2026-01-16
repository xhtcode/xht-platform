package com.xht.modules.admin.system.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.tree.INode;
import com.xht.api.system.enums.MenuStatusEnums;
import com.xht.api.system.enums.MenuTypeEnums;
import com.xht.modules.admin.system.converter.SysMenuConverter;
import com.xht.modules.admin.system.dao.SysMenuDao;
import com.xht.modules.admin.system.entity.SysMenuEntity;
import com.xht.api.system.domain.form.SysMenuForm;
import com.xht.api.system.domain.query.SysMenuQuery;
import com.xht.api.system.domain.response.SysMenuResponse;
import com.xht.modules.admin.system.service.ISysMenuService;
import com.xht.modules.admin.system.utils.MenuValidationFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.xht.modules.admin.constant.MenuConstant.DEFAULT_PARENT_ID;

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
    public void create(SysMenuForm form) {
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
    private void checkExitsParentMenu(SysMenuForm form) {
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
    public void updateById(SysMenuForm form) {
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
    public List<INode<Long>> findTree(SysMenuQuery query) {
        List<SysMenuEntity> list = sysMenuDao.getMenuList(query);
        return sysMenuConverter.toTree(list);
    }

    /**
     * 根据条件查询是否包含菜单类型为button菜单列表(树形结构)
     *
     * @return 菜单树形结构信息
     */
    @Override
    public List<INode<Long>> getMenuTreeSystemTool() {
        List<SysMenuEntity> list = sysMenuDao.getMenuTreeSystemTool(false);
        return sysMenuConverter.toTree(list);
    }

}
