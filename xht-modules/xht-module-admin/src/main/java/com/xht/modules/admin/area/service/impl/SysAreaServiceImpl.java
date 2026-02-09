package com.xht.modules.admin.area.service.impl;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.modules.admin.area.AreaConstant;
import com.xht.modules.admin.area.converter.SysAreaConverter;
import com.xht.modules.admin.area.dao.SysAreaDao;
import com.xht.modules.admin.area.domain.form.SysAreaForm;
import com.xht.modules.admin.area.domain.response.SysAreaResponse;
import com.xht.modules.admin.area.entity.SysAreaEntity;
import com.xht.modules.admin.area.enums.AreaHasChildEnums;
import com.xht.modules.admin.area.service.ISysAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 系统管理-行政区划 Service实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAreaServiceImpl implements ISysAreaService {

    private final SysAreaDao sysAreaDao;

    private final SysAreaConverter sysAreaConverter;

    /**
     * 添加系统管理-行政区划
     *
     * @param form 系统管理-行政区划
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysAreaForm form) {
        checkForm(form);
        sysAreaDao.save(sysAreaConverter.toEntity(form));
        if (!Objects.equals(form.getParentId(), AreaConstant.DEFAULT_PARENT_CODE)) {
            sysAreaDao.updateHasChild(form.getParentId(), AreaHasChildEnums.HAS_CHILD);
        }
    }

    /**
     * 根据主键`id`批量删除系统管理-行政区划
     *
     * @param id 系统管理-行政区划主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        boolean childStatus = sysAreaDao.existsChild(id);
        ThrowUtils.throwIf(childStatus, "该区划下有子区划，请先删除子区划");
        sysAreaDao.removeById(id);
    }

    /**
     * 修改系统管理-行政区划
     *
     * @param form 系统管理-行政区划
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(SysAreaForm form) {
        checkForm(form);
        SysAreaEntity entity = sysAreaConverter.toEntity(form);
        sysAreaDao.updateById(entity);
        if (!Objects.equals(form.getParentId(), AreaConstant.DEFAULT_PARENT_CODE)) {
            sysAreaDao.updateHasChild(form.getParentId(), AreaHasChildEnums.HAS_CHILD);
        }
    }

    /**
     * 根据主键`id`查询系统管理-行政区划
     *
     * @param id 系统管理-行政区划主键
     * @return 系统管理-行政区划信息
     */
    @Override
    public SysAreaResponse findById(Long id) {
        SysAreaEntity areaEntity = sysAreaDao.findById(id);
        return sysAreaConverter.toResponse(areaEntity);
    }

    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentId 上级系统管理-行政区划ID
     * @return 系统管理-行政区划列表
     */
    @Override
    public List<SysAreaResponse> listByParentId(Long parentId) {
        if (parentId < AreaConstant.DEFAULT_ID) {
            return Collections.emptyList();
        }
        List<SysAreaEntity> sysAreaEntities = sysAreaDao.listByParentId(parentId);
        return sysAreaConverter.toResponse(sysAreaEntities);
    }


    /**
     * 校验表单
     *
     * @param form 表单
     */
    private void checkForm(SysAreaForm form) {
        ThrowUtils.notNull(form, "表单参数不能为空");
        Long parentId = form.getParentId();
        if (!Objects.equals(parentId, AreaConstant.DEFAULT_PARENT_CODE)) {
            Boolean parentArea = sysAreaDao.exists(SysAreaEntity::getId, parentId);
            ThrowUtils.throwIf(!parentArea, "上级区划代码不存在");
        }
        boolean existsAreaCode = sysAreaDao.existsAreaCode(form.getAreaCode(), form.getId());
        ThrowUtils.throwIf(existsAreaCode, "区划编码已存在");
    }

}




