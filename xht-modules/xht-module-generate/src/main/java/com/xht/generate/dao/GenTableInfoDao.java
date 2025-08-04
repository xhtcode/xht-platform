package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;


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

}
