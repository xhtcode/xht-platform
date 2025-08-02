package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenProjectFormRequest;
import com.xht.generate.domain.request.GenProjectQueryRequest;
import com.xht.generate.domain.response.GenProjectResponse;

/**
 * 项目管理Service接口
 *
 * @author xht
 **/
public interface IGenProjectService {


    /**
     * 创建项目
     *
     * @param formRequest 项目表单请求参数
     * @return 操作结果
     */
    Boolean create(GenProjectFormRequest formRequest);

    /**
     * 根据ID删除项目
     *
     * @param id 项目ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新项目
     *
     * @param formRequest 项目更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenProjectFormRequest formRequest);

    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    GenProjectResponse getById(Long id);

    /**
     * 分页查询项目
     *
     * @param queryRequest 项目查询请求参数
     * @return 项目分页信息
     */
    PageResponse<GenProjectResponse> selectPage(GenProjectQueryRequest queryRequest);


}
