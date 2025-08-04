package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.request.GenColumnInfoFormRequest;
import com.xht.generate.domain.request.GenColumnInfoQueryRequest;


/**
 * 字段信息管理 Dao
 *
 * @author xht
 **/
public interface GenColumnInfoDao extends MapperRepository<GenColumnInfoEntity> {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    Boolean updateFormRequest(GenColumnInfoFormRequest formRequest);

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    Page<GenColumnInfoEntity> queryPageRequest(Page<GenColumnInfoEntity> page, GenColumnInfoQueryRequest queryRequest);

}
