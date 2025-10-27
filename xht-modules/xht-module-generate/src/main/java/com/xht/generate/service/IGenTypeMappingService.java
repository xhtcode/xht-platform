package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.form.GenTypeMappingBasicForm;
import com.xht.generate.domain.query.GenTypeMappingBasicQuery;
import com.xht.generate.domain.response.GenTypeMappingResponse;

import java.util.List;

/**
 * 字段映射管理Service接口
 *
 * @author xht
 **/
public interface IGenTypeMappingService {

    /**
     * 创建字段映射
     *
     * @param form 字段映射表单请求参数
     */
    void create(GenTypeMappingBasicForm form);

    /**
     * 根据ID删除字段映射
     *
     * @param id 字段映射ID
     */
    void removeById(Long id);

    /**
     * 根据ID更新字段映射
     *
     * @param form 字段映射更新请求参数
     */
    void updateById(GenTypeMappingBasicForm form);

    /**
     * 根据ID查询字段映射
     *
     * @param id 字段映射ID
     * @return 字段映射信息
     */
    GenTypeMappingResponse findById(Long id);

    /**
     * 分页查询字段映射
     *
     * @param query 字段映射查询请求参数
     * @return 字段映射分页信息
     */
    PageResponse<GenTypeMappingResponse>findPageList(GenTypeMappingBasicQuery query);

    /**
     * 根据数据库类型和目标编程语言类型查询所有的映射关系
     *
     * @param query 字段映射查询请求参数
     * @return 字段映射信息
     */
    List<GenTypeMappingResponse> findAll(GenTypeMappingBasicQuery query);
}
