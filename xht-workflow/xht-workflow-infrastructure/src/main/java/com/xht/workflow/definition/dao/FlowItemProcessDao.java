package com.xht.workflow.definition.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.entity.FlowItemProcessEntity;

import java.util.List;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
public interface FlowItemProcessDao extends MapperRepository<FlowItemProcessEntity> {

    /**
     * 更新流程定义信息
     *
     * @param id   ID
     * @param form 表单参数
     */
    void updateFormRequest(Long id, FlowItemProcessForm form);

    /**
     * 查询流程定义列表
     *
     * @param query 查询参数
     * @return 流程定义列表
     */
    List<FlowItemProcessEntity> findList(FlowItemProcessPageQuery query);

}
