package com.xht.workflow.process.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.process.dao.FlowTimeLimitDao;
import com.xht.workflow.process.dao.mapper.FlowTimeLimitMapper;
import com.xht.workflow.process.domain.query.FlowTimeLimitPageQuery;
import com.xht.workflow.process.entity.FlowTimeLimitEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 流程扩展-流程时限
 *
 * @author xht
 */
@Slf4j
@Repository
public class FlowTimeLimitDaoImpl extends MapperRepositoryImpl<FlowTimeLimitMapper, FlowTimeLimitEntity> implements FlowTimeLimitDao {

    @Override
    protected SFunction<FlowTimeLimitEntity, ?> getFieldId() {
        return FlowTimeLimitEntity::getId;
    }


    /**
     * 分页查询流程时限
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    @Override
    public Page<FlowTimeLimitEntity> findPageList(Page<FlowTimeLimitEntity> page, FlowTimeLimitPageQuery query) {
        LambdaQueryWrapper<FlowTimeLimitEntity> queryWrapper = new LambdaQueryWrapper<>();
        //@formatter:off
        queryWrapper
                .eq(condition(query.getBusinessId()), FlowTimeLimitEntity::getBusinessId, query.getBusinessId());
        //@formatter:on
        return page(page, queryWrapper);
    }

}
