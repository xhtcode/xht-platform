package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenTypeMappingFormRequest;
import com.xht.generate.domain.request.GenTypeMappingQueryRequest;
import com.xht.generate.domain.response.GenTypeMappingResponse;

/**
 * 字段映射管理Service接口
 *
 * @author xht
 **/
public interface IGenTypeMappingService {

    /**
     * 创建字段映射
     *
     * @param formRequest 字段映射表单请求参数
     * @return 操作结果
     */
    Boolean create(GenTypeMappingFormRequest formRequest);

    /**
     * 根据ID删除字段映射
     *
     * @param id 字段映射ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新字段映射
     *
     * @param formRequest 字段映射更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenTypeMappingFormRequest formRequest);

    /**
     * 根据ID查询字段映射
     *
     * @param id 字段映射ID
     * @return 字段映射信息
     */
    GenTypeMappingResponse getById(Long id);

    /**
     * 分页查询字段映射
     *
     * @param queryRequest 字段映射查询请求参数
     * @return 字段映射分页信息
     */
    PageResponse<GenTypeMappingResponse> selectPage(GenTypeMappingQueryRequest queryRequest);

}
