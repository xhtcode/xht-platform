package com.xht.workflow.definition.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.domain.form.FlowDefinitionForm;
import com.xht.workflow.definition.domain.query.FlowDefinitionPageQuery;
import com.xht.workflow.definition.entity.FlowDefinitionEntity;
import com.xht.workflow.definition.enums.DefinitionStatusEnum;

import java.util.List;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
public interface IFlowDefinitionDao extends MapperRepository<FlowDefinitionEntity> {

    /**
     * 更新
     *
     * @param id            ID
     * @param form          表单参数
     * @param categoryLevel 定义级别
     */
    void updateRequest(Long id, FlowDefinitionForm form, Integer categoryLevel);

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
     * @param flowDefinitionPageQuery 流程定义查询参数
     * @return 流程定义列表
     */
    List<FlowDefinitionEntity> findList(FlowDefinitionPageQuery flowDefinitionPageQuery);

    /**
     * 根据父级ID查询流程类别
     *
     * @param parentId           父级ID
     * @param definitionStatusEnum 定义状态
     * @return 流程类别列表
     */
    List<FlowDefinitionEntity> findByParentId(Long parentId, DefinitionStatusEnum definitionStatusEnum);

}
