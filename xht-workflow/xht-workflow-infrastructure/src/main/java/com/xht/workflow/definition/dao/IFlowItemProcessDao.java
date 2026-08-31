package com.xht.workflow.definition.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.entity.FlowItemProcessEntity;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
public interface IFlowItemProcessDao extends MapperRepository<FlowItemProcessEntity> {

    /**
     * 更新流程定义信息
     *
     * @param id   ID
     * @param form 表单参数
     */
    void updateFormRequest(Long id, FlowItemProcessForm form);

    /**
     * 分页查询流程定义
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    Page<FlowItemProcessEntity> findPageList(Page<FlowItemProcessEntity> page, FlowItemProcessPageQuery query);

}
