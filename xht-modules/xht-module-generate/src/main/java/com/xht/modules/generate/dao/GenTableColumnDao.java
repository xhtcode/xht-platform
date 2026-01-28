package com.xht.modules.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.generate.domain.form.GenColumnInfoForm;
import com.xht.modules.generate.entity.GenTableColumnEntity;

import java.util.List;


/**
 * 字段信息管理 Dao
 *
 * @author xht
 **/
public interface GenTableColumnDao extends MapperRepository<GenTableColumnEntity> {

    /**
     * 根据表ID删除字段信息
     *
     * @param tableId 表ID
     */
    void deleteByTableId(Long tableId);

    /**
     * 修改字段信息
     *
     * @param column 字段信息
     */
    void updateFormRequest(GenColumnInfoForm column);

    /**
     * 根据表ID查询字段信息
     *
     * @param tableId 表ID
     * @return 字段信息
     */
    List<GenTableColumnEntity> findByTableId(Long tableId);
}
