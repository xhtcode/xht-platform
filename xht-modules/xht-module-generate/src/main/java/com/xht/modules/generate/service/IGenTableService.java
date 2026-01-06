package com.xht.modules.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.generate.domain.form.ImportTableForm;
import com.xht.modules.generate.domain.form.TableColumnForm;
import com.xht.modules.generate.domain.query.DataBaseQuery;
import com.xht.modules.generate.domain.query.GenTableInfoQuery;
import com.xht.modules.generate.domain.response.GenTableResponse;
import com.xht.modules.generate.domain.vo.TableColumnVo;

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
     * @param form 表信息表单请求参数
     */
    void importTable(ImportTableForm form);

    /**
     * 同步表信息
     *
     * @param tableId 表id
     */
    void syncTable(Long tableId);

    /**
     * 根据ID删除表信息
     *
     * @param tableId 表信息ID
     */
    void removeById(Long tableId);

    /**
     * 根据ID更新表信息
     *
     * @param form 表信息更新请求参数
     */
    void updateById(TableColumnForm form);

    /**
     * 根据ID查询表信息
     *
     * @param id 表信息ID
     * @return 表信息字段信息
     */
    TableColumnVo findById(Long id);


    /**
     * 分页查询已存在的表信息
     *
     * @param query 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    PageResponse<GenTableResponse> selectExistsPage(GenTableInfoQuery query);

    /**
     * 分页查询不存在的表信息
     *
     * @param query 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    List<GenTableResponse> selectNoExistsList(DataBaseQuery query);

}
