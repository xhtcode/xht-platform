package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenTemplateGroupFormRequest;
import com.xht.generate.domain.request.GenTemplateGroupQueryRequest;
import com.xht.generate.domain.response.GenTemplateGroupResponse;

/**
 * 项目管理Service接口
 *
 * @author xht
 **/
public interface IGenTemplateGroupService {


    /**
     * 创建项目
     *
     * @param formRequest 项目表单请求参数
     * @return 操作结果
     */
    Boolean create(GenTemplateGroupFormRequest formRequest);

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
    Boolean updateById(GenTemplateGroupFormRequest formRequest);

    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    GenTemplateGroupResponse getById(Long id);

    /**
     * 分页查询项目
     *
     * @param queryRequest 项目查询请求参数
     * @return 项目分页信息
     */
    PageResponse<GenTemplateGroupResponse> selectPage(GenTemplateGroupQueryRequest queryRequest);


}
