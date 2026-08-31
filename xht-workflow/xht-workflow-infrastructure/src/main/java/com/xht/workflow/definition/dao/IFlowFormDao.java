package com.xht.workflow.definition.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.domain.form.FlowFormForm;
import com.xht.workflow.definition.domain.query.FlowFormPageQuery;
import com.xht.workflow.definition.entity.FlowFormEntity;

/**
 * 流程扩展-流程表单
 *
 * @author xht
 */
public interface IFlowFormDao extends MapperRepository<FlowFormEntity> {

    /**
     * 更新表单信息
     *
     * @param id   ID
     * @param form 表单参数
     */
    void updateFormRequest(Long id, FlowFormForm form);

    /**
     * 分页查询流程表单
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    Page<FlowFormEntity> findPageList(Page<FlowFormEntity> page, FlowFormPageQuery query);

}
