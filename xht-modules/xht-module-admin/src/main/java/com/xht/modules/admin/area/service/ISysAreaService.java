package com.xht.modules.admin.area.service;

import com.xht.modules.admin.area.domain.form.SysAreaForm;
import com.xht.modules.admin.area.domain.response.SysAreaResponse;

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
     *
     */
    void create(SysAreaForm form);

    /**
     * 根据主键`id`批量删除系统管理-行政区划
     *
     * @param id 系统管理-行政区划主键
     */
    void remove(Long id);

    /**
     * 修改系统管理-行政区划
     *
     * @param form 系统管理-行政区划
     *
     */
    void updateById(SysAreaForm form);

    /**
     * 根据主键`id`查询系统管理-行政区划
     *
     * @param id 系统管理-行政区划主键
     * @return 系统管理-行政区划信息
     */
    SysAreaResponse findById(Long id);

    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentId 上级系统管理-行政区划ID
     * @return 系统管理-行政区划列表
     */
    List<SysAreaResponse> listByParentId(Long parentId);
}
