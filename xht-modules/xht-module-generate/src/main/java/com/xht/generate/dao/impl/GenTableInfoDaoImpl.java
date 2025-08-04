package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTableInfoDao;
import com.xht.generate.dao.mapper.GenTableInfoMapper;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 表信息管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTableInfoDaoImpl extends MapperRepositoryImpl<GenTableInfoMapper, GenTableInfoEntity> implements GenTableInfoDao {


    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenTableInfoFormRequest formRequest) {
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
    public Page<GenTableInfoEntity> queryPageRequest(Page<GenTableInfoEntity> page, GenTableInfoQueryRequest queryRequest) {
        return null;
    }
}
