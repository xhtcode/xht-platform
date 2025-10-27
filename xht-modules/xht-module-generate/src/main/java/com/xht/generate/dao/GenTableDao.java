package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTableEntity;
import com.xht.generate.domain.form.GenTableInfoBasicForm;
import com.xht.generate.domain.query.GenTableInfoBasicQuery;

import java.util.List;


/**
 * 表信息管理 Dao
 *
 * @author xht
 **/
public interface GenTableDao extends MapperRepository<GenTableEntity> {

    /**
     * 更新菜单信息
     *
     * @param form 菜单信息
     */
    void updateFormRequest(GenTableInfoBasicForm form);

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param query 菜单查询请求参数
     * @return 菜单分页信息
     */
    Page<GenTableEntity> findPageList(Page<GenTableEntity> page, GenTableInfoBasicQuery query);

    /**
     * 根据数据源id查询表名
     *
     * @param dataSourceId 数据源id
     * @return 表名
     */
    List<String> findTableNameByDbId(Long dataSourceId);
}
