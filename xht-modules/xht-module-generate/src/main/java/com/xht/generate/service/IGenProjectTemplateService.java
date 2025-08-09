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


}
