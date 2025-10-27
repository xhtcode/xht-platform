package com.xht.system.modules.log.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.log.dao.SysLogDao;
import com.xht.system.modules.log.dao.mapper.SysLogMapper;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogBasicQuery;
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
     * @param page  分页信息
     * @param query 系统日志管理查询请求参数
     * @return 分页查询系统日志
     */
    @Override
    public Page<SysLogEntity> findPageList(Page<SysLogEntity> page, SysLogBasicQuery query) {
        LambdaQueryWrapper<SysLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        condition(query.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysLogEntity::getTitle, query.getKeyWord())
                                .or()
                                .like(SysLogEntity::getServiceName, query.getKeyWord())
                                .or()
                                .like(SysLogEntity::getDescription, query.getKeyWord())
                )
                .like(condition(query.getTitle()), SysLogEntity::getTitle, query.getTitle())
                .like(condition(query.getServiceName()), SysLogEntity::getServiceName, query.getServiceName())
                .like(condition(query.getDescription()), SysLogEntity::getDescription, query.getDescription())
                .eq(condition(query.getStatus()), SysLogEntity::getStatus, query.getStatus())
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
