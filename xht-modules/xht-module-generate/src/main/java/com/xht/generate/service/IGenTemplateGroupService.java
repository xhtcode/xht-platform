package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.form.GenTemplateGroupFormRequest;
import com.xht.generate.domain.query.GenTemplateGroupQueryRequest;
import com.xht.generate.domain.response.GenTemplateGroupResponse;

import java.util.List;

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
     * 获取代码生成模板组列表
     *
     * @return 代码生成模板组列表响应结果
     */
    List<GenTemplateGroupResponse> findAll();

    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param queryRequest 查询参数
     * @return 代码生成模板组列表响应结果
     */
    PageResponse<GenTemplateGroupResponse> selectPage(GenTemplateGroupQueryRequest queryRequest);
}
