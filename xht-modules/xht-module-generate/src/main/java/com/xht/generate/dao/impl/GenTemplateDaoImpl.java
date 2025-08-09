package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.dao.mapper.GenTemplateMapper;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenTemplateFormRequest;
import com.xht.generate.domain.request.GenTemplateQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 模板管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTemplateDaoImpl extends MapperRepositoryImpl<GenTemplateMapper, GenTemplateEntity> implements GenTemplateDao {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenTemplateFormRequest formRequest) {
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
    public Page<GenTemplateEntity> queryPageRequest(Page<GenTemplateEntity> page, GenTemplateQueryRequest queryRequest) {
        return null;
    }
}
