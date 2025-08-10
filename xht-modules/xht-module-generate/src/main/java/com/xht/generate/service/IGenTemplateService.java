package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenTemplateFormRequest;
import com.xht.generate.domain.request.GenTemplateQueryRequest;
import com.xht.generate.domain.response.GenTemplateResponse;

/**
 * 模板管理Service接口
 *
 * @author xht
 **/
public interface IGenTemplateService {

    /**
     * 创建模板
     *
     * @param formRequest 模板表单请求参数
     * @return 操作结果
     */
    Boolean create(GenTemplateFormRequest formRequest);

    /**
     * 根据ID删除模板
     *
     * @param id 模板ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新模板
     *
     * @param formRequest 模板更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenTemplateFormRequest formRequest);

    /**
     * 根据ID查询模板
     *
     * @param id 模板ID
     * @return 模板信息
     */
    GenTemplateResponse getById(Long id);

    /**
     * 分页查询模板
     *
     * @param queryRequest 模板查询请求参数
     * @return 模板分页信息
     */
    PageResponse<GenTemplateResponse> selectPage(GenTemplateQueryRequest queryRequest);

}
