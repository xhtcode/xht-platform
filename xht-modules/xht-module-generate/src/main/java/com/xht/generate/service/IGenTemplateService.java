package com.xht.generate.service;

import com.xht.generate.domain.form.GenTemplateForm;
import com.xht.generate.domain.response.GenTemplateResponse;

import java.util.List;

/**
 * 模板管理Service接口
 *
 * @author xht
 **/
public interface IGenTemplateService {

    /**
     * 创建模板
     *
     * @param form 模板表单请求参数
     */
    Long create(GenTemplateForm form);

    /**
     * 根据ID删除模板
     *
     * @param id 模板ID
     */
    void removeById(Long id);

    /**
     * 根据ID更新模板
     *
     * @param form 模板更新请求参数
     */
    void updateById(GenTemplateForm form);

    /**
     * 根据ID查询模板
     *
     * @param id 模板ID
     * @return 模板信息
     */
    GenTemplateResponse findById(Long id);

    /**
     * 根据模板组ID获取模板列表
     * @param groupId 模板组ID
     * @return 模板响应列表
     */
    List<GenTemplateResponse> listByGroupId(String groupId);
}
