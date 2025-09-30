package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.form.GenTemplateGroupForm;
import com.xht.generate.domain.query.GenTemplateGroupQuery;
import com.xht.generate.domain.response.GenTemplateGroupResp;

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
     * @param form 项目表单请求参数
     */
    void create(GenTemplateGroupForm form);

    /**
     * 根据ID删除项目
     *
     * @param id 项目ID
     */
    void removeById(Long id);

    /**
     * 根据ID更新项目
     *
     * @param form 项目更新请求参数
     */
    void updateById(GenTemplateGroupForm form);

    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    GenTemplateGroupResp findById(Long id);

    /**
     * 获取代码生成模板组列表
     *
     * @return 代码生成模板组列表响应结果
     */
    List<GenTemplateGroupResp> findAll();

    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param query 查询参数
     * @return 代码生成模板组列表响应结果
     */
    PageResponse<GenTemplateGroupResp> pageList(GenTemplateGroupQuery query);
}
