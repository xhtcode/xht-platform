package com.xht.modules.admin.audit.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.audit.dao.BLogDao;
import com.xht.modules.admin.audit.dao.mapper.BLogMapper;
import com.xht.modules.admin.audit.domain.query.BLogQuery;
import com.xht.modules.admin.audit.entity.BLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 *
 * @author xht
 **/
@Slf4j
@Repository
public class BLogDaoImpl extends MapperRepositoryImpl<BLogMapper, BLogEntity> implements BLogDao {

    /**
     * 分页查询系统日志
     *
     * @param page  分页信息
     * @param query 系统日志管理查询请求参数
     * @return 分页查询系统日志
     */
    @Override
    public Page<BLogEntity> findPageList(Page<BLogEntity> page, BLogQuery query) {
        LambdaQueryWrapper<BLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        condition(query.getKeyWord()), wrapper -> wrapper.or()
                                .like(BLogEntity::getTitle, query.getKeyWord())
                                .or()
                                .like(BLogEntity::getServiceName, query.getKeyWord())
                                .or()
                                .like(BLogEntity::getDescription, query.getKeyWord())
                )
                .like(condition(query.getTitle()), BLogEntity::getTitle, query.getTitle())
                .like(condition(query.getServiceName()), BLogEntity::getServiceName, query.getServiceName())
                .like(condition(query.getDescription()), BLogEntity::getDescription, query.getDescription())
                .eq(condition(query.getStatus()), BLogEntity::getStatus, query.getStatus())
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
    protected SFunction<BLogEntity, ?> getFieldId() {
        return BLogEntity::getId;
    }
}
