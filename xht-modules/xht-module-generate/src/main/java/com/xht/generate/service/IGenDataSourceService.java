package com.xht.generate.service;

import com.xht.generate.domain.form.GenDataSourceFormRequest;
import com.xht.generate.domain.query.GenDataSourceQueryRequest;
import com.xht.generate.domain.response.GenDataSourceResponse;

import java.util.List;

/**
 * 数据源管理Service接口
 *
 * @author xht
 **/
public interface IGenDataSourceService {


    /**
     * 创建数据源
     *
     * @param formRequest 数据源表单请求参数
     * @return 操作结果
     */
    Boolean create(GenDataSourceFormRequest formRequest);

    /**
     * 根据ID删除数据源
     *
     * @param id 数据源ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID更新数据源
     *
     * @param formRequest 数据源更新请求参数
     * @return 操作结果
     */
    Boolean updateById(GenDataSourceFormRequest formRequest);

    /**
     * 根据ID查询数据源
     *
     * @param id 数据源ID
     * @return 数据源信息
     */
    GenDataSourceResponse findById(Long id);

    /**
     * 按条件查询数据源
     *
     * @param queryRequest 数据源查询请求参数
     * @return 数据源分页信息
     */
    List<GenDataSourceResponse> findList(GenDataSourceQueryRequest queryRequest);

    /**
     * 测试链接
     *
     * @param id 数据源ID
     * @return 测试结果 true:成功 false:失败
     */
    Boolean connection(Long id);

}
