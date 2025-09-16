package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.query.DataBaseQueryRequest;
import com.xht.generate.domain.form.GenTableInfoFormRequest;
import com.xht.generate.domain.query.GenTableInfoQueryRequest;
import com.xht.generate.domain.form.ImportTableFormRequest;
import com.xht.generate.domain.response.GenTableResponse;
import com.xht.generate.domain.vo.GenTableColumnVo;

import java.util.List;

/**
 * 表信息管理Service接口
 *
 * @author xht
 **/
public interface IGenTableService {

    /**
     * 导入表
     *
     * @param formRequest 表信息表单请求参数
     * @return 操作结果
     */
    Boolean importTable(ImportTableFormRequest formRequest);

    /**
     * 同步表信息
     *
     * @param tableId 表id
     * @return 操作结果 true表示成功，false表示失败
     */
    Boolean syncTable(Long tableId);

    /**
     * 根据ID删除表信息
     *
     * @param tableId 表信息ID
     * @return 操作结果
     */
    Boolean removeById(Long tableId);

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
     * @return 表信息字段信息
     */
    GenTableColumnVo getById(Long id);


    /**
     * 分页查询已存在的表信息
     *
     * @param queryRequest 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    PageResponse<GenTableResponse> selectExistsPage(GenTableInfoQueryRequest queryRequest);

    /**
     * 分页查询不存在的表信息
     *
     * @param queryRequest 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    List<GenTableResponse> selectNoExistsList(DataBaseQueryRequest queryRequest);

}
