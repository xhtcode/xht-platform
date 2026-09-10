package com.xht.platform.area.service;

import com.xht.platform.area.domain.form.SysAreaForm;
import com.xht.platform.area.domain.response.SysAreaResponse;

import java.util.List;

/**
 * 系统管理-行政区划 Service
 *
 * @author xht
 */
public interface ISysAreaService {

    /**
     * 添加系统管理-行政区划
     *
     * @param form 系统管理-行政区划
     */
    void create(SysAreaForm form);

    /**
     * 根据主键`areaId`删除系统管理-行政区划
     *
     * @param areaId 系统管理-行政区划主键
     */
    void remove(Long areaId);

    /**
     * 修改系统管理-行政区划
     *
     * @param areaId 系统管理-行政区划主键
     * @param form 系统管理-行政区划
     */
    void updateById(Long areaId, SysAreaForm form);

    /**
     * 根据主键`areaId`查询系统管理-行政区划
     *
     * @param areaId 系统管理-行政区划主键
     * @return 系统管理-行政区划信息
     */
    SysAreaResponse findById(Long areaId);

    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentAreaCode 上级区划编码
     * @return 系统管理-行政区划列表
     */
    List<SysAreaResponse> listByParentId(String parentAreaCode);

}
