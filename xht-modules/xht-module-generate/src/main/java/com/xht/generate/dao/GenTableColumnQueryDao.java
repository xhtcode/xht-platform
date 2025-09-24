package com.xht.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTableColumnQueryEntity;

import java.util.List;

/**
 * @author xht
 **/
public interface GenTableColumnQueryDao extends MapperRepository<GenTableColumnQueryEntity> {

    /**
     * 根据表ID查询字段信息
     *
     * @param tableId 表ID
     * @return 字段信息
     */
    List<GenTableColumnQueryEntity> findByTableId(Long tableId);

    /**
     * 根据表ID删除字段信息
     *
     * @param tableId 表ID
     */
    void deleteByTableId(Long tableId);
}
