package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;

import java.util.List;


/**
 * 表信息管理 Dao
 *
 * @author xht
 **/
public interface GenTableInfoDao extends MapperRepository<GenTableInfoEntity> {

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
    Page<GenTableInfoEntity> queryPageRequest(Page<GenTableInfoEntity> page, GenTableInfoQueryRequest queryRequest);

    /**
     * 根据数据源id查询表名
     *
     * @param dataSourceId 数据源id
     * @return 表名
     */
    List<String> findTableNameByDbId(Long dataSourceId);
}
