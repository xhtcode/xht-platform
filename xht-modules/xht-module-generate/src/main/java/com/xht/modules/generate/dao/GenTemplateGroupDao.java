package com.xht.modules.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.generate.entity.GenTemplateGroupEntity;
import com.xht.modules.generate.domain.form.GenTemplateGroupForm;
import com.xht.modules.generate.domain.query.GenTemplateGroupQuery;

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
    void updateFormRequest(GenTemplateGroupForm form);

    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param page         分页查询参数
     * @param query 查询参数
     * @return 代码生成模板组列表响应结果
     */
    Page<GenTemplateGroupEntity> findPageList(Page<GenTemplateGroupEntity> page, GenTemplateGroupQuery query);

    /**
     * 查询所有
     *
     * @return 列表
     */
    List<GenTemplateGroupEntity> findAllBy();

    /**
     * 根据ID更新模板数量
     *
     * @param id               模板组ID
     * @param templateCountOld 模板数量
     * @param templateCountNew 新的模板数量
     */
    void updateTemplateCountById(Long id, Integer templateCountOld, int templateCountNew);
}
