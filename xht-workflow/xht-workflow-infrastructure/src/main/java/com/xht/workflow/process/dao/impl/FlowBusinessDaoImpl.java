package com.xht.workflow.process.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.process.dao.FlowBusinessDao;
import com.xht.workflow.process.dao.mapper.FlowBusinessMapper;
import com.xht.workflow.process.domain.form.FlowBusinessForm;
import com.xht.workflow.process.domain.query.FlowBusinessPageQuery;
import com.xht.workflow.process.entity.FlowBusinessEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程扩展-流程业务
 *
 * @author xht
 */
@Slf4j
@Repository
public class FlowBusinessDaoImpl extends MapperRepositoryImpl<FlowBusinessMapper, FlowBusinessEntity> implements FlowBusinessDao {

    @Override
    protected SFunction<FlowBusinessEntity, ?> getFieldId() {
        return FlowBusinessEntity::getId;
    }

    /**
     * 分页查询流程业务
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    @Override
    public Page<FlowBusinessEntity> findPageList(Page<FlowBusinessEntity> page, FlowBusinessPageQuery query) {
        LambdaQueryWrapper<FlowBusinessEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.isQuick()) {
            //@formatter:off
            queryWrapper.and(
                    condition(query.getKeyWord()), wrapper -> wrapper.or()
                            .like(FlowBusinessEntity::getItemCode, query.getKeyWord())
                            .or()
                            .like(FlowBusinessEntity::getItemName, query.getKeyWord())
                            .or()
                            .like(FlowBusinessEntity::getBusinessNumber, query.getKeyWord())
            );
            //@formatter:on
        } else {
            //@formatter:off
            queryWrapper
                    .like(condition(query.getItemCode()), FlowBusinessEntity::getItemCode, query.getItemCode())
                    .like(condition(query.getItemName()), FlowBusinessEntity::getItemName, query.getItemName())
                    .eq(condition(query.getItemType()), FlowBusinessEntity::getItemType, query.getItemType())
                    .like(condition(query.getBusinessNumber()), FlowBusinessEntity::getBusinessNumber, query.getBusinessNumber());
            //@formatter:on
        }
        return page(page, queryWrapper);
    }

}
