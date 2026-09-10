package com.xht.platform.area.service;

import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.ThrowUtils;
import com.xht.platform.area.AreaConstant;
import com.xht.platform.area.converter.SysAreaConverter;
import com.xht.platform.area.dao.SysAreaDao;
import com.xht.platform.area.domain.form.SysAreaForm;
import com.xht.platform.area.domain.response.SysAreaResponse;
import com.xht.platform.area.entity.SysAreaEntity;
import com.xht.platform.area.enums.AreaHasChildEnum;
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
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysAreaForm form) {
        checkForm(null, form);
        sysAreaDao.save(sysAreaConverter.toEntity(form));
        if (!Objects.equals(form.getParentAreaCode(), AreaConstant.DEFAULT_PARENT_CODE)) {
            sysAreaDao.updateHasChild(form.getParentAreaCode(), AreaHasChildEnum.HAS_CHILD);
        }
    }

    /**
     * 根据主键`id`删除系统管理-行政区划
     *
     * @param areaId 系统管理-行政区划主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long areaId) {
        SysAreaEntity areaEntity = sysAreaDao.findById(areaId);
        ThrowUtils.notNull(areaEntity, "区划不存在");
        boolean childStatus = sysAreaDao.existsChild(areaEntity.getAreaCode());
        ThrowUtils.throwIf(childStatus, "该区划下有子区划，请先删除子区划");
        sysAreaDao.removeById(areaId);
    }

    /**
     * 修改系统管理-行政区划
     *
     * @param areaId 系统管理-行政区划主键
     * @param form   系统管理-行政区划
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Long areaId, SysAreaForm form) {
        checkForm(areaId, form);
        sysAreaDao.updateFormRequest(areaId, form);
        if (!Objects.equals(form.getParentAreaCode(), AreaConstant.DEFAULT_PARENT_CODE)) {
            sysAreaDao.updateHasChild(form.getParentAreaCode(), AreaHasChildEnum.HAS_CHILD);
        }
    }


    /**
     * 根据主键`id`查询系统管理-行政区划
     *
     * @param areaId 系统管理-行政区划主键
     * @return 系统管理-行政区划信息
     */
    @Override
    public SysAreaResponse findById(Long areaId) {
        SysAreaEntity areaEntity = sysAreaDao.findById(areaId);
        return sysAreaConverter.toResponse(areaEntity);
    }

    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentAreaCode 上级区划编码
     * @return 系统管理-行政区划列表
     */
    @Override
    public List<SysAreaResponse> listByParentId(String parentAreaCode) {
        if (!StringUtils.hasText(parentAreaCode)) {
            return Collections.emptyList();
        }
        List<SysAreaEntity> sysAreaEntities = sysAreaDao.listByParentId(parentAreaCode);
        return sysAreaConverter.toResponse(sysAreaEntities);
    }


    /**
     * 校验表单
     *
     * @param areaId 系统管理-行政区划主键（创建时传null）
     * @param form   表单
     */
    private void checkForm(Long areaId, SysAreaForm form) {
        ThrowUtils.notNull(form, "表单参数不能为空");
        String parentAreaCode = form.getParentAreaCode();
        if (!Objects.equals(parentAreaCode, AreaConstant.DEFAULT_PARENT_CODE)) {
            Boolean parentArea = sysAreaDao.exists(SysAreaEntity::getAreaCode, parentAreaCode);
            ThrowUtils.throwIf(!parentArea, "上级区划编码不存在");
        }
        boolean existsAreaCode = sysAreaDao.existsAreaCode(form.getAreaCode(), areaId);
        ThrowUtils.throwIf(existsAreaCode, "区划编码已存在");
    }

}




