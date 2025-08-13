package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.request.DataBaseQueryRequest;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;
import com.xht.generate.domain.request.ImportTableFormRequest;
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
    Boolean importTable(ImportTableFormRequest formRequest);

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
     * 分页查询已存在的表信息
     *
     * @param queryRequest 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    PageResponse<GenTableInfoResponse> selectExistsPage(GenTableInfoQueryRequest queryRequest);

    /**
     * 分页查询不存在的表信息
     *
     * @param queryRequest 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    PageResponse<GenTableInfoResponse> selectNoExistsPage(DataBaseQueryRequest queryRequest);
}
