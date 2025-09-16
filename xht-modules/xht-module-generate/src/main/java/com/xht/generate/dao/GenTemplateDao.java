package com.xht.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.form.GenTemplateFormRequest;


/**
 * 模板管理 Dao
 *
 * @author xht
 **/
public interface GenTemplateDao extends MapperRepository<GenTemplateEntity> {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    Boolean updateFormRequest(GenTemplateFormRequest formRequest);

}
