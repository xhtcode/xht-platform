package com.xht.workflow.definition;

import com.xht.framework.common.domain.R;
import com.xht.workflow.definition.domain.response.FlowDefinitionResponse;
import com.xht.workflow.definition.service.IFlowApplyService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述：申请控制器
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "流程扩展-申请控制器", description = "流程扩展-申请控制器相关的API")
@RestController
@RequestMapping("/workflow/apply")
@RequiredArgsConstructor
public class FlowApplyController {

    private final IFlowApplyService flowApplyService;

    /**
     * 根据父ID查询流程类别
     *
     * @param parentId 父ID
     * @return 流程类别列表
     */
    @Schema(description = "查询流程类别")
    @GetMapping("/category/items/{parentId}")
    public R<List<FlowDefinitionResponse>> getFlowCategoryItems(@PathVariable Long parentId) {
        return R.ok().build(flowApplyService.getFlowCategoryItems(parentId));
    }

}
