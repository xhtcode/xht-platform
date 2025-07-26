package com.xht.system.modules.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogQueryRequest;

/**
 * 系统日志
 *
 * @author xht
 **/
public interface SysLogDao extends MapperRepository<SysLogEntity> {

    /**
     * 分页查询系统日志
     *
     * @param page         分页信息
     * @param queryRequest 系统日志管理查询请求参数
     * @return 分页查询系统日志
     */
    Page<SysLogEntity> queryPageRequest(Page<SysLogEntity> page, SysLogQueryRequest queryRequest);
}
