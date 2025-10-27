package com.xht.generate.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.form.GenTemplateBasicForm;

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
    void updateFormRequest(GenTemplateBasicForm form);

    /**
     * 根据分组查询模板信息
     *
     * @param groupId 分组ID
     * @return 模板信息
     */
    List<GenTemplateEntity> findByGroupId(String groupId);
}
