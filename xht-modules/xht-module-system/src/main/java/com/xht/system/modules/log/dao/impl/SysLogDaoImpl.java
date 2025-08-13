package com.xht.system.modules.log.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.log.dao.SysLogDao;
import com.xht.system.modules.log.dao.mapper.SysLogMapper;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysLogDaoImpl extends MapperRepositoryImpl<SysLogMapper, SysLogEntity> implements SysLogDao {

    /**
     * 分页查询系统日志
     *
     * @param page         分页信息
     * @param queryRequest 系统日志管理查询请求参数
     * @return 分页查询系统日志
     */
    @Override
    public Page<SysLogEntity> queryPageRequest(Page<SysLogEntity> page, SysLogQueryRequest queryRequest) {
        LambdaQueryWrapper<SysLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        condition(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysLogEntity::getTitle, queryRequest.getKeyWord())
                                .or()
                                .like(SysLogEntity::getServiceName, queryRequest.getKeyWord())
                                .or()
                                .like(SysLogEntity::getDescription, queryRequest.getKeyWord())
                )
                .like(condition(queryRequest.getTitle()), SysLogEntity::getTitle, queryRequest.getTitle())
                .like(condition(queryRequest.getServiceName()), SysLogEntity::getServiceName, queryRequest.getServiceName())
                .like(condition(queryRequest.getDescription()), SysLogEntity::getDescription, queryRequest.getDescription())
                .eq(condition(queryRequest.getStatus()), SysLogEntity::getStatus, queryRequest.getStatus())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysLogEntity, ?> getFieldId() {
        return SysLogEntity::getId;
    }
}
