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
     * 删除系统管理-行政区划
     *
     * @param cityCode 系统管理-行政区划ID
     *
     */
    void removeById(String cityCode);

    /**
     * 修改系统管理-行政区划
     *
     * @param form 系统管理-行政区划
     *
     */
    void updateById(SysAreaForm form);

    /**
     * 查询系统管理-行政区划
     *
     * @param cityCode 系统管理-行政区划ID
     * @return 系统管理-行政区划
     */
    SysAreaResponse findById(String cityCode);


    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentCode 上级系统管理-行政区划ID
     * @return 系统管理-行政区划列表
     */
    List<SysAreaResponse> findListByParentCode(String parentCode);

}
