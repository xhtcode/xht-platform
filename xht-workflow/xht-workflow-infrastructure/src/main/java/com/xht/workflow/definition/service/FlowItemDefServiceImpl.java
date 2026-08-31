package com.xht.workflow.definition.service;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.common.constant.CategoryConstant;
import com.xht.workflow.definition.converter.FlowDefinitionConverter;
import com.xht.workflow.definition.dao.IFlowItemDefDao;
import com.xht.workflow.definition.domain.form.FlowItemDefForm;
import com.xht.workflow.definition.domain.query.FlowItemDefPageQuery;
import com.xht.workflow.definition.domain.response.FlowItemDefResponse;
import com.xht.workflow.definition.entity.FlowItemDefEntity;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 描述： 流程定义服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowItemDefServiceImpl implements IFlowItemDefService {

    private final IFlowItemDefDao flowCategoryDao;

    private final FlowDefinitionConverter flowDefinitionConverter;

    /**
     * 创建流程定义
     *
     * @param form 流程定义信息
     */
    @Override
    public void create(FlowItemDefForm form) {
        Long parentId = form.getParentId();
        FlowItemDefEntity entity = flowDefinitionConverter.toEntity(form);
        if (parentId == null || parentId < 0) {
            form.setParentId(CategoryConstant.DEFAULT_CATEGORY_ID);
        } else {
            FlowItemDefEntity parentEntity = flowCategoryDao.findById(parentId);
            ThrowUtils.throwIf(Objects.isNull(parentEntity), BusinessErrorCode.DATA_NOT_EXIST, "父级定义不存在");
            ThrowUtils.throwIf(Objects.equals(parentEntity.getItemType(), FlowDefinitionTypeEnum.ORDER), BusinessErrorCode.PARAM_ERROR, "父级定义是事项，禁止添加添加子项");
            entity.setItemLevel(parentEntity.getItemLevel() + 1);
        }
        // 检查流程定义是否存在
        Boolean checkDictCode = flowCategoryDao.checkCategoryCode(null, form.getItemCode());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "流程定义已存在，禁止添加");
        flowCategoryDao.saveTransactional(entity);
    }

    /**
     * 删除流程定义
     *
     * @param id ) 流程定义ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        Boolean exists = flowCategoryDao.exists(FlowItemDefEntity::getParentId, id);
        ThrowUtils.throwIf(exists, BusinessErrorCode.PARAM_ERROR, "此条数据存在子项禁止删除数据");
        flowCategoryDao.removeById(id);
    }


    /**
     * 修改流程定义
     *
     * @param id   流程定义ID
     * @param form 流程定义信息
     */
    @Override
    public void updateById(Long id, FlowItemDefForm form) {
        Long parentId = form.getParentId();
        ThrowUtils.notNull(id);
        FlowItemDefEntity flowItemDefEntity = flowCategoryDao.findOptionalById(id).orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST));
        Integer categoryLevel = CategoryConstant.DEFAULT_CATEGORY_LEVEL;
        if (parentId == null || parentId <= CategoryConstant.DEFAULT_CATEGORY_ID) {
            form.setParentId(CategoryConstant.DEFAULT_CATEGORY_ID);
        } else {
            FlowItemDefEntity parentEntity = flowCategoryDao.findById(parentId);
            ThrowUtils.throwIf(Objects.isNull(parentEntity), BusinessErrorCode.DATA_NOT_EXIST, "父级定义不存在");
            ThrowUtils.throwIf(Objects.equals(parentEntity.getItemType(), FlowDefinitionTypeEnum.ORDER), BusinessErrorCode.PARAM_ERROR, "父级定义是事项，数据错误请联系管理员!");
            categoryLevel = parentEntity.getItemLevel() + 1;
        }
        if (!Objects.equals(form.getItemStatus(), flowItemDefEntity.getItemStatus())) {
            Boolean exists = flowCategoryDao.exists(FlowItemDefEntity::getParentId, form.getParentId());
            ThrowUtils.throwIf(exists, BusinessErrorCode.PARAM_ERROR, "存在子项禁止修改状态");
        }
        if (!Objects.equals(form.getItemType(), flowItemDefEntity.getItemType())) {
            Boolean exists = flowCategoryDao.exists(FlowItemDefEntity::getParentId, id);
            ThrowUtils.throwIf(exists, BusinessErrorCode.PARAM_ERROR, "存在子项禁止修改类别");
        }
        // 检查流程定义是否存在
        Boolean checkDictCode = flowCategoryDao.checkCategoryCode(id, form.getItemCode());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "定义编码已存在，禁止修改");
        flowCategoryDao.updateRequest(id, form, categoryLevel);
    }

    /**
     * 获取流程定义详情
     *
     * @param id 流程定义ID
     * @return 流程定义详情
     */
    @Override
    public FlowItemDefResponse findById(Long id) {
        return flowDefinitionConverter.toResponse(flowCategoryDao.findById(id));
    }

    /**
     * 获取流程定义列表
     *
     * @param flowItemDefPageQuery 流程定义查询参数
     * @return 流程定义列表
     */
    @Override
    public List<FlowItemDefResponse> findList(FlowItemDefPageQuery flowItemDefPageQuery) {
        return flowDefinitionConverter.toResponse(flowCategoryDao.findList(flowItemDefPageQuery));
    }
}
