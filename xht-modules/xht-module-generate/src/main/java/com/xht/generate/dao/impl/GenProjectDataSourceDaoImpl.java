package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenProjectDataSourceDao;
import com.xht.generate.dao.mapper.GenProjectDataSourceMapper;
import com.xht.generate.domain.entity.GenProjectDataSourceEntity;
import com.xht.generate.domain.request.GenProjectDataSourceFormRequest;
import com.xht.generate.domain.request.GenProjectDataSourceQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 项目数据源管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class GenProjectDataSourceDaoImpl extends MapperRepositoryImpl<GenProjectDataSourceMapper, GenProjectDataSourceEntity> implements GenProjectDataSourceDao {


    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenProjectDataSourceFormRequest formRequest) {
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
    public Page<GenProjectDataSourceEntity> queryPageRequest(Page<GenProjectDataSourceEntity> page, GenProjectDataSourceQueryRequest queryRequest) {
        return null;
    }
}
