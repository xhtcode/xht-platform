package com.xht.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTemplateGroupEntity;
import com.xht.generate.domain.request.GenTemplateGroupFormRequest;


/**
 * 项目管理 Dao
 *
 * @author xht
 **/
public interface GenTemplateGroupDao extends MapperRepository<GenTemplateGroupEntity> {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    Boolean updateFormRequest(GenTemplateGroupFormRequest formRequest);


}
