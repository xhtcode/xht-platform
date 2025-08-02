package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenProjectDataSourceFormRequest;
import com.xht.generate.domain.request.GenProjectDataSourceQueryRequest;
import com.xht.generate.domain.response.GenProjectDataSourceResponse;

/**
 * 项目数据源管理Service接口
 *
 * @author xht
 **/
public interface IGenProjectDataSourceService {


    /**
     * 创建项目数据源
     *
     * @param formRequest 项目数据源表单请求参数
     * @return 操作结果
     */
    Boolean create(GenProjectDataSourceFormRequest formRequest);

    /**
     * 根据ID删除项目数据源
     *
     * @param id 项目数据源ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新项目数据源
     *
     * @param formRequest 项目数据源更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenProjectDataSourceFormRequest formRequest);

    /**
     * 根据ID查询项目数据源
     *
     * @param id 项目数据源ID
     * @return 项目数据源信息
     */
    GenProjectDataSourceResponse getById(Long id);

    /**
     * 分页查询项目数据源
     *
     * @param queryRequest 项目数据源查询请求参数
     * @return 项目数据源分页信息
     */
    PageResponse<GenProjectDataSourceResponse> selectPage(GenProjectDataSourceQueryRequest queryRequest);


}
