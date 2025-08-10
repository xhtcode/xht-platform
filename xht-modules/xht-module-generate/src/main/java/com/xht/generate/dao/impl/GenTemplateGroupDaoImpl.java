package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTemplateGroupDao;
import com.xht.generate.dao.mapper.GenTemplateGroupMapper;
import com.xht.generate.domain.entity.GenTemplateGroupEntity;
import com.xht.generate.domain.request.GenTemplateGroupFormRequest;
import com.xht.generate.domain.request.GenTemplateGroupQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 项目管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTemplateGroupDaoImpl extends MapperRepositoryImpl<GenTemplateGroupMapper, GenTemplateGroupEntity> implements GenTemplateGroupDao {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenTemplateGroupFormRequest formRequest) {
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
    public Page<GenTemplateGroupEntity> queryPageRequest(Page<GenTemplateGroupEntity> page, GenTemplateGroupQueryRequest queryRequest) {
        return null;
    }

}
