package com.xht.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.request.GenColumnInfoFormRequest;


/**
 * 字段信息管理 Dao
 *
 * @author xht
 **/
public interface GenColumnInfoDao extends MapperRepository<GenColumnInfoEntity> {

    /**
     * 根据表ID删除字段信息
     *
     * @param tableId 表ID
     */
    void deleteByTableId(String tableId);

    /**
     * 修改字段信息
     *
     * @param column 字段信息
     */
    void updateFormRequest(GenColumnInfoFormRequest column);
}
