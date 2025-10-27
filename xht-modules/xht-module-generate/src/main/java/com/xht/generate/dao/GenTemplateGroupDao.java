package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTemplateGroupEntity;
import com.xht.generate.domain.form.GenTemplateGroupBasicForm;
import com.xht.generate.domain.query.GenTemplateGroupBasicQuery;

import java.util.List;


/**
 * 项目管理 Dao
 *
 * @author xht
 **/
public interface GenTemplateGroupDao extends MapperRepository<GenTemplateGroupEntity> {

    /**
     * 更新菜单信息
     *
     * @param form 菜单信息
     */
    void updateFormRequest(GenTemplateGroupBasicForm form);

    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param page         分页查询参数
     * @param query 查询参数
     * @return 代码生成模板组列表响应结果
     */
    Page<GenTemplateGroupEntity> findPageList(Page<GenTemplateGroupEntity> page, GenTemplateGroupBasicQuery query);

    /**
     * 查询所有
     *
     * @return 列表
     */
    List<GenTemplateGroupEntity> findAllBy();

}
