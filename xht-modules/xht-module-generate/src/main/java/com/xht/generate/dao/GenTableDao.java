package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTableEntity;
import com.xht.generate.domain.form.GenTableInfoFormRequest;
import com.xht.generate.domain.query.GenTableInfoQueryRequest;

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
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    Boolean updateFormRequest(GenTableInfoFormRequest formRequest);

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    Page<GenTableEntity> queryPageRequest(Page<GenTableEntity> page, GenTableInfoQueryRequest queryRequest);

    /**
     * 根据数据源id查询表名
     *
     * @param dataSourceId 数据源id
     * @return 表名
     */
    List<String> findTableNameByDbId(Long dataSourceId);
}
