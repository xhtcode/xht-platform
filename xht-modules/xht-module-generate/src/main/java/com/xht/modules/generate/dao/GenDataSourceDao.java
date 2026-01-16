package com.xht.modules.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.generate.entity.GenDataSourceEntity;
import com.xht.modules.generate.domain.form.GenDataSourceForm;
import com.xht.modules.generate.domain.query.GenDataSourceQuery;

import java.util.List;


/**
 * 数据源管理 Dao
 *
 * @author xht
 **/
public interface GenDataSourceDao extends MapperRepository<GenDataSourceEntity> {

    /**
     * 更新数据源信息
     *
     * @param form 数据源信息
     */
    void updateFormRequest(GenDataSourceForm form);

    /**
     * 分页查询数据源
     *
     * @param query 数据源查询请求参数
     * @return 数据源分页信息
     */
    List<GenDataSourceEntity> findList(GenDataSourceQuery query);


}
