package com.xht.workflow.process.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.process.domain.form.FlowTimeLimitForm;
import com.xht.workflow.process.domain.query.FlowTimeLimitPageQuery;
import com.xht.workflow.process.entity.FlowTimeLimitEntity;

import java.time.LocalDateTime;

/**
 * 流程扩展-流程时限
 *
 * @author xht
 */
public interface FlowTimeLimitDao extends MapperRepository<FlowTimeLimitEntity> {

    /**
     * 分页查询流程时限
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    Page<FlowTimeLimitEntity> findPageList(Page<FlowTimeLimitEntity> page, FlowTimeLimitPageQuery query);

}
