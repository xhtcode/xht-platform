package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenProjectTemplateEntity;
import com.xht.generate.domain.request.GenProjectTemplateFormRequest;
import com.xht.generate.domain.request.GenProjectTemplateQueryRequest;


/**
 * 项目模板管理 Dao
 *
 * @author xht
 **/
public interface GenProjectTemplateDao extends MapperRepository<GenProjectTemplateEntity> {

}
