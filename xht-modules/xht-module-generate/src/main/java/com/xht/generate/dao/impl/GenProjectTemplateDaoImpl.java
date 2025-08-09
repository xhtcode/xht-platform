package com.xht.generate.dao.impl;

import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenProjectTemplateDao;
import com.xht.generate.dao.mapper.GenProjectTemplateMapper;
import com.xht.generate.domain.entity.GenProjectTemplateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 项目模板管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenProjectTemplateDaoImpl extends MapperRepositoryImpl<GenProjectTemplateMapper, GenProjectTemplateEntity> implements GenProjectTemplateDao {

}
