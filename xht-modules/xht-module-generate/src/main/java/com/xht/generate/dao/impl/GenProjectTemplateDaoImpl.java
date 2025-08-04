package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenProjectTemplateDao;
import com.xht.generate.dao.mapper.GenProjectTemplateMapper;
import com.xht.generate.domain.entity.GenProjectTemplateEntity;
import com.xht.generate.domain.request.GenProjectTemplateFormRequest;
import com.xht.generate.domain.request.GenProjectTemplateQueryRequest;
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


    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenProjectTemplateFormRequest formRequest) {
        return null;
    }

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public Page<GenProjectTemplateEntity> queryPageRequest(Page<GenProjectTemplateEntity> page, GenProjectTemplateQueryRequest queryRequest) {
        return null;
    }
}
