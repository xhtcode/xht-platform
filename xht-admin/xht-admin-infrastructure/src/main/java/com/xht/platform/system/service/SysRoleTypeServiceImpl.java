package com.xht.platform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.CollectionUtils;
import com.xht.platform.system.converter.SysMenuConverter;
import com.xht.platform.system.converter.SysRoleTypeConverter;
import com.xht.platform.system.dao.SysMenuDao;
import com.xht.platform.system.dao.SysRoleDao;
import com.xht.platform.system.dao.SysRoleTypeDao;
import com.xht.platform.system.dao.SysRoleTypeMenuDao;
import com.xht.platform.system.domain.form.SysRoleTypeForm;
import com.xht.platform.system.domain.form.SysRoleTypeMenuBindForm;
import com.xht.platform.system.domain.query.SysRoleTypeQuery;
import com.xht.platform.system.domain.response.RoleSelectedMenuResponse;
import com.xht.platform.system.domain.response.SysRoleTypeResponse;
import com.xht.platform.system.entity.SysMenuEntity;
import com.xht.platform.system.entity.SysRoleEntity;
import com.xht.platform.system.entity.SysRoleTypeEntity;
import com.xht.platform.system.entity.SysRoleTypeMenuEntity;
import com.xht.platform.system.enums.RoleStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色类型service实现类
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleTypeServiceImpl implements ISysRoleTypeService {

    private final SysRoleTypeDao sysRoleTypeDao;

    private final SysRoleTypeMenuDao sysRoleTypeMenuDao;

    private final SysRoleDao sysRoleDao;

    private final SysMenuDao sysMenuDao;

    private final SysRoleTypeConverter sysRoleTypeConverter;

    private final SysMenuConverter sysMenuConverter;

    /**
     * 创建角色类型
     *
     * @param form 角色类型表单请求参数
     */
    @Override
    public void create(SysRoleTypeForm form) {
        Boolean exists = sysRoleTypeDao.existsTypeCode(null, form.getTypeCode());
        AssertUtils.isTrue(!exists, BusinessErrorCode.DATA_EXIST, "角色类型编码已存在");
        SysRoleTypeEntity entity = sysRoleTypeConverter.toEntity(form);
        sysRoleTypeDao.saveTransactional(entity);
    }

    /**
     * 删除角色类型
     *
     * @param id 角色类型id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        Boolean exists = sysRoleTypeDao.exists(SysRoleTypeEntity::getId, id);
        AssertUtils.isTrue(exists, BusinessErrorCode.DATA_NOT_EXIST, "角色类型不存在");
        Boolean roleExists = sysRoleDao.exists(SysRoleEntity::getRoleTypeId, id);
        AssertUtils.isTrue(!roleExists, BusinessErrorCode.DATA_EXIST, "角色类型已分配角色，禁止删除");
        sysRoleTypeDao.removeById(id);
    }

    /**
     * 根据ID更新角色类型
     *
     * @param id   角色类型ID
     * @param form 角色类型更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Long id, SysRoleTypeForm form) {
        Boolean exists = sysRoleTypeDao.existsTypeCode(id, form.getTypeCode());
        AssertUtils.isTrue(!exists, BusinessErrorCode.DATA_EXIST, "角色类型编码已存在");
        Boolean typeExists = sysRoleTypeDao.exists(SysRoleTypeEntity::getId, id);
        AssertUtils.isTrue(typeExists, BusinessErrorCode.DATA_NOT_EXIST, "角色类型不存在");
        sysRoleTypeDao.updateFormRequest(id, form);
    }

    /**
     * 根据ID查询角色类型
     *
     * @param id 角色类型ID
     * @return 角色类型信息
     */
    @Override
    public SysRoleTypeResponse findById(Long id) {
        SysRoleTypeEntity entity = sysRoleTypeDao.findOptionalById(id).orElse(null);
        return sysRoleTypeConverter.toResponse(entity);
    }

    /**
     * 分页查询角色类型
     *
     * @param query 角色类型查询请求参数
     * @return 角色类型分页信息
     */
    @Override
    public PageResponse<SysRoleTypeResponse> findPageList(SysRoleTypeQuery query) {
        Page<SysRoleTypeEntity> page = sysRoleTypeDao.findPageList(PageTool.getPage(query), query);
        return sysRoleTypeConverter.toResponse(page);
    }

    /**
     * 查询所有角色类型
     *
     * @return 角色类型列表
     */
    @Override
    public List<SysRoleTypeResponse> list() {
        return sysRoleTypeConverter.toResponse(sysRoleTypeDao.findListByStatus(RoleStatusEnum.NORMAL));
    }

    /**
     * 角色类型绑定菜单
     *
     * @param bindRequest 角色类型菜单绑定请求
     */
    @Override
    public void roleTypeMenuBind(SysRoleTypeMenuBindForm bindRequest) {
        Long roleTypeId = bindRequest.getRoleTypeId();
        Boolean typeExists = sysRoleTypeDao.exists(SysRoleTypeEntity::getId, roleTypeId);
        AssertUtils.isTrue(typeExists, BusinessErrorCode.DATA_NOT_EXIST, "角色类型不存在");
        List<Long> menuIds = bindRequest.getMenuIds();
        List<SysRoleTypeMenuEntity> roleTypeMenuEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menuIds)) {
            Boolean menuExists = sysMenuDao.existsMenuIds(menuIds);
            AssertUtils.isTrue(menuExists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
            for (Long item : menuIds) {
                SysRoleTypeMenuEntity entity = new SysRoleTypeMenuEntity();
                entity.setRoleTypeId(roleTypeId);
                entity.setMenuId(item);
                roleTypeMenuEntities.add(entity);
            }
        }
        sysRoleTypeMenuDao.roleTypeMenuBind(roleTypeId, roleTypeMenuEntities);
    }

    /**
     * 根据角色类型ID获取已选菜单信息
     *
     * @param roleTypeId 角色类型ID
     * @return {@link RoleSelectedMenuResponse}
     */
    @Override
    public RoleSelectedMenuResponse selectMenuIdByTypeId(String roleTypeId) {
        AssertUtils.hasText(roleTypeId, "角色类型ID不能为空");
        Long typeId = Long.valueOf(roleTypeId);
        RoleSelectedMenuResponse response = new RoleSelectedMenuResponse();
        List<Long> menuIdByTypeId = sysRoleTypeMenuDao.findMenuIdByTypeId(typeId);
        List<SysMenuEntity> menuEntityList = sysMenuDao.getMenuTreeSystemTool(true);
        response.setCheckedMenuIds(menuIdByTypeId);
        response.setMenuTotal(menuEntityList.size());
        response.setMenuList(sysMenuConverter.toTree(menuEntityList));
        return response;
    }

}
