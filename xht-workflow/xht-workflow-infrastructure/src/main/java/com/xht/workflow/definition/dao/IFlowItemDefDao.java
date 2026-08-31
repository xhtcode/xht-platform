package com.xht.workflow.definition.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.domain.form.FlowItemDefForm;
import com.xht.workflow.definition.domain.query.FlowItemDefPageQuery;
import com.xht.workflow.definition.entity.FlowItemDefEntity;
import com.xht.workflow.definition.enums.FlowDefinitionStatusEnum;

import java.util.List;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
public interface IFlowItemDefDao extends MapperRepository<FlowItemDefEntity> {

    /**
     * 更新
     *
     * @param id            ID
     * @param form          表单参数
     * @param categoryLevel 定义级别
     */
    void updateRequest(Long id, FlowItemDefForm form, Integer categoryLevel);

    /**
     * 校验定义编号
     *
     * @param id           ID
     * @param categoryCode 定义编号
     * @return 校验结果
     */
    Boolean checkCategoryCode(Long id, String categoryCode);

    /**
     * 获取流程定义列表
     *
     * @param flowItemDefPageQuery 流程定义查询参数
     * @return 流程定义列表
     */
    List<FlowItemDefEntity> findList(FlowItemDefPageQuery flowItemDefPageQuery);

    /**
     * 根据父级ID查询流程类别
     *
     * @param parentId           父级ID
     * @param flowDefinitionStatusEnum 定义状态
     * @return 流程类别列表
     */
    List<FlowItemDefEntity> findByParentId(Long parentId, FlowDefinitionStatusEnum flowDefinitionStatusEnum);

}
