package com.xht.modules.admin.audit.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.audit.domain.query.BLogQuery;
import com.xht.modules.admin.audit.entity.BLogEntity;

/**
 * 系统日志
 *
 * @author xht
 **/
public interface BLogDao extends MapperRepository<BLogEntity> {

    /**
     * 分页查询系统日志
     *
     * @param page  分页信息
     * @param query 系统日志管理查询请求参数
     * @return 分页查询系统日志
     */
    Page<BLogEntity> findPageList(Page<BLogEntity> page, BLogQuery query);
}
