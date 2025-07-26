package com.xht.framework.mybatis.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.core.domain.request.PageQueryRequest;

/**
 * 扩展Repository接口
 *
 * @author xht
 **/
public interface ExpansionRepository<T, queryRequest extends PageQueryRequest, formRequest extends FormRequest> {

    /**
     * 分页查询列表
     *
     * @param queryRequest 分页查询请求参数
     * @return 分页列表
     */
    Page<T> queryPageRequest(queryRequest queryRequest);

    /**
     * 根据更新请求更新指定ID的实体
     *
     * @param formRequest 更新请求参数
     * @return 更新结果 true：成功，false：失败
     */
    boolean updateFormRequest(formRequest formRequest);
}
