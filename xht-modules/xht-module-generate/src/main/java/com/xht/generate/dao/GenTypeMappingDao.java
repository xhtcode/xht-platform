package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.form.GenTypeMappingBasicForm;
import com.xht.generate.domain.query.GenTypeMappingBasicQuery;


/**
 * 字段映射管理 Dao
 *
 * @author xht
 **/
public interface GenTypeMappingDao extends MapperRepository<GenTypeMappingEntity> {

    /**
     * 更新菜单信息
     *
     * @param form 菜单信息
     */
    void updateFormRequest(GenTypeMappingBasicForm form);

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param query 菜单查询请求参数
     * @return 菜单分页信息
     */
    Page<GenTypeMappingEntity> findPageList(Page<GenTypeMappingEntity> page, GenTypeMappingBasicQuery query);

}
