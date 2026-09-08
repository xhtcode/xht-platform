package com.xht.workflow.process.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.process.domain.query.FlowBusinessPageQuery;
import com.xht.workflow.process.entity.FlowBusinessEntity;

/**
 * 流程扩展-流程业务
 *
 * @author xht
 */
public interface FlowBusinessDao extends MapperRepository<FlowBusinessEntity> {

    /**
     * 分页查询流程业务
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    Page<FlowBusinessEntity> findPageList(Page<FlowBusinessEntity> page, FlowBusinessPageQuery query);

}
