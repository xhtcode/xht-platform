package com.xht.modules.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.generate.entity.GenTemplateEntity;
import com.xht.modules.generate.domain.form.GenTemplateForm;

import java.util.List;


/**
 * 模板管理 Dao
 *
 * @author xht
 **/
public interface GenTemplateDao extends MapperRepository<GenTemplateEntity> {

    /**
     * 更新模板信息
     *
     * @param form 模板信息
     */
    void updateFormRequest(GenTemplateForm form);

    /**
     * 根据分组查询模板信息
     *
     * @param groupId 分组ID
     * @return 模板信息
     */
    List<GenTemplateEntity> findByGroupId(String groupId);

}
