package com.xht.modules.generate.service;

import com.xht.modules.generate.domain.form.GenDataSourceForm;
import com.xht.modules.generate.domain.query.GenDataSourceQuery;
import com.xht.modules.generate.domain.response.GenDataSourceResponse;

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
     * @param form 数据源表单请求参数
     */
    void create(GenDataSourceForm form);

    /**
     * 根据ID删除数据源
     *
     * @param id 数据源ID
     */
    void removeById(Long id);

    /**
     * 根据ID更新数据源
     *
     * @param form 数据源更新请求参数
     */
    void updateById(GenDataSourceForm form);

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
     * @param query 数据源查询请求参数
     * @return 数据源分页信息
     */
    List<GenDataSourceResponse> findList(GenDataSourceQuery query);

    /**
     * 测试链接
     *
     * @param id 数据源ID
     * @return 测试结果 true:成功 false:失败
     */
    Boolean connection(Long id);

}
