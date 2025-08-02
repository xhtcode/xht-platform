package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenProjectTemplateFormRequest;
import com.xht.generate.domain.request.GenProjectTemplateQueryRequest;
import com.xht.generate.domain.response.GenProjectTemplateResponse;

/**
 * 项目模板管理Service接口
 *
 * @author xht
 **/
public interface IGenProjectTemplateService {


    /**
     * 创建项目模板
     *
     * @param formRequest 项目模板表单请求参数
     * @return 操作结果
     */
    Boolean create(GenProjectTemplateFormRequest formRequest);

    /**
     * 根据ID删除项目模板
     *
     * @param id 项目模板ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新项目模板
     *
     * @param formRequest 项目模板更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenProjectTemplateFormRequest formRequest);

    /**
     * 根据ID查询项目模板
     *
     * @param id 项目模板ID
     * @return 项目模板信息
     */
    GenProjectTemplateResponse getById(Long id);

    /**
     * 分页查询项目模板
     *
     * @param queryRequest 项目模板查询请求参数
     * @return 项目模板分页信息
     */
    PageResponse<GenProjectTemplateResponse> selectPage(GenProjectTemplateQueryRequest queryRequest);


}
