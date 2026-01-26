package com.xht.modules.admin.area.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xht.modules.admin.area.converter.SysAreaConverter;
import com.xht.modules.admin.area.dao.SysAreaDao;
import com.xht.modules.admin.area.domain.form.SysAreaForm;
import com.xht.modules.admin.area.domain.response.SysAreaResponse;
import com.xht.modules.admin.area.entity.SysAreaEntity;
import com.xht.modules.admin.area.service.ISysAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        sysAreaDao.save(sysAreaConverter.toEntity(form));
    }

    /**
     * 删除系统管理-行政区划
     *
     * @param cityCode 系统管理-行政区划ID
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(String cityCode) {
        sysAreaDao.removeById(cityCode);
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
        SysAreaEntity entity = sysAreaConverter.toEntity(form);
        sysAreaDao.updateById(entity);
    }

    /**
     * 查询系统管理-行政区划
     *
     * @param cityCode 系统管理-行政区划ID
     * @return 系统管理-行政区划
     */
    @Override
    public SysAreaResponse findById(String cityCode) {
        SysAreaEntity areaEntity = sysAreaDao.findById(cityCode);
        return sysAreaConverter.toResponse(areaEntity);
    }

    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentCode 上级系统管理-行政区划ID
     * @return 系统管理-行政区划列表
     */
    @Override
    public List<SysAreaResponse> findListByParentCode(String parentCode) {
        List<SysAreaEntity> sysAreaEntities = sysAreaDao.listByParentCode(StrUtil.emptyToDefault(parentCode, "1"));
        return sysAreaConverter.toResponse(sysAreaEntities);
    }

}




