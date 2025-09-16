package com.xht.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.form.GenDataSourceFormRequest;
import com.xht.generate.domain.query.GenDataSourceQueryRequest;

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
     * @param formRequest 数据源信息
     * @return 是否成功
     */
    Boolean updateFormRequest(GenDataSourceFormRequest formRequest);

    /**
     * 分页查询数据源
     *
     * @param queryRequest 数据源查询请求参数
     * @return 数据源分页信息
     */
    List<GenDataSourceEntity> selectList(GenDataSourceQueryRequest queryRequest);


}
