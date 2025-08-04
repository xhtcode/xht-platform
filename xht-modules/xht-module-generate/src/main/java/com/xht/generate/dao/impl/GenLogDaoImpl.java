package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenLogDao;
import com.xht.generate.dao.mapper.GenLogMapper;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.domain.request.GenLogFormRequest;
import com.xht.generate.domain.request.GenLogQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 生成日志管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenLogDaoImpl extends MapperRepositoryImpl<GenLogMapper, GenLogEntity> implements GenLogDao {


    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenLogFormRequest formRequest) {
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
    public Page<GenLogEntity> queryPageRequest(Page<GenLogEntity> page, GenLogQueryRequest queryRequest) {
        return null;
    }
}
