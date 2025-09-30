package com.xht.generate.service;

import com.xht.generate.domain.response.GenTableColumnResp;

import java.util.List;

/**
 * 字段信息管理Service接口
 *
 * @author xht
 **/
public interface IGenTableColumnService {

    /**
     * 根据ID查询字段信息
     *
     * @param id 字段信息ID
     * @return 字段信息信息
     */
    GenTableColumnResp findById(Long id);

    /**
     * 根据表id查询字段信息
     *
     * @param tableId 表id
     * @return 字段信息分页信息
     */
    List<GenTableColumnResp> listByTableId(String tableId);

}
