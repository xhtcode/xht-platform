package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenColumnInfoFormRequest;
import com.xht.generate.domain.request.GenColumnInfoQueryRequest;
import com.xht.generate.domain.response.GenColumnInfoResponse;

/**
 * 字段信息管理Service接口
 *
 * @author xht
 **/
public interface IGenColumnInfoService {


    /**
     * 创建字段信息
     *
     * @param formRequest 字段信息表单请求参数
     * @return 操作结果
     */
    Boolean create(GenColumnInfoFormRequest formRequest);

    /**
     * 根据ID更新字段信息
     *
     * @param formRequest 字段信息更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenColumnInfoFormRequest formRequest);

    /**
     * 根据ID查询字段信息
     *
     * @param id 字段信息ID
     * @return 字段信息信息
     */
    GenColumnInfoResponse getById(Long id);

    /**
     * 分页查询字段信息
     *
     * @param queryRequest 字段信息查询请求参数
     * @return 字段信息分页信息
     */
    PageResponse<GenColumnInfoResponse> selectPage(GenColumnInfoQueryRequest queryRequest);


}
