package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;
import com.xht.generate.domain.response.GenTableInfoResponse;

/**
 * 表信息管理Service接口
 *
 * @author xht
 **/
public interface IGenTableInfoService {


    /**
     * 导入表
     *
     * @param formRequest 表信息表单请求参数
     * @return 操作结果
     */
    Boolean importTable(GenTableInfoFormRequest formRequest);

    /**
     * 根据ID删除表信息
     *
     * @param id 表信息ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新表信息
     *
     * @param formRequest 表信息更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenTableInfoFormRequest formRequest);

    /**
     * 根据ID查询表信息
     *
     * @param id 表信息ID
     * @return 表信息信息
     */
    GenTableInfoResponse getById(Long id);

    /**
     * 分页查询表信息
     *
     * @param queryRequest 表信息查询请求参数
     * @return 表信息分页信息
     */
    PageResponse<GenTableInfoResponse> selectPage(GenTableInfoQueryRequest queryRequest);


}
