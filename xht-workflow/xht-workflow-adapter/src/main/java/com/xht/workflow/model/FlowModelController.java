package com.xht.workflow.model;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.model.domain.form.FlowModelDesignForm;
import com.xht.workflow.model.domain.form.FlowModelInitForm;
import com.xht.workflow.model.domain.form.FlowModelUpdateForm;
import com.xht.workflow.model.domain.query.FlowModelPageQuery;
import com.xht.workflow.model.domain.response.FlowModelResponse;
import com.xht.workflow.model.service.IFlowModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述： 流程模型管理
 *
 * @author xht
 **/
@Tag(name = "流程模型管理", description = "流程模型管理")
@Slf4j
@RestController
@RequestMapping("/workflow/model")
@RequiredArgsConstructor
public class FlowModelController {

    private final IFlowModelService flowModelService;

    /**
     * 初始化流程模型
     *
     * @param modelInitForm 流程模型初始化信息
     */
    @Operation(summary = "初始化流程模型")
    @PostMapping("/init")
    public R<Void> modelInit(@Validated @RequestBody FlowModelInitForm modelInitForm) {
        flowModelService.modelInit(modelInitForm);
        return R.ok().build();
    }

    /**
     * 删除流程模型
     *
     * @param modelId 流程模型ID
     */
    @Operation(summary = "删除流程模型")
    @PostMapping("/remove")
    public R<Void> removeModelById(@RequestParam("modelId") String modelId) {
        flowModelService.removeModelById(modelId);
        return R.ok().build();
    }

    /**
     * 更新流程模型
     *
     * @param modelUpdateForm 流程模型更新信息
     */
    @Operation(summary = "更新流程模型")
    @PostMapping("/update")
    public R<Void> updateModel(@Validated @RequestBody FlowModelUpdateForm modelUpdateForm) {
        flowModelService.updateModel(modelUpdateForm);
        return R.ok().build();
    }

    /**
     * 设计流程模型
     *
     * @param modelDesignForm 流程模型设计信息
     */
    @Operation(summary = "设计流程模型")
    @PostMapping("/design")
    public R<Void> modelDesign(@Validated @RequestBody FlowModelDesignForm modelDesignForm) {
        flowModelService.modelDesign(modelDesignForm);
        return R.ok().build();
    }

    /**
     * 部署流程模型
     *
     * @param modelId 流程模型ID
     */
    @Operation(summary = "部署流程模型")
    @PostMapping("/deploy")
    public R<Void> deployModel(@RequestParam("modelId") String modelId) {
        flowModelService.deployModel(modelId);
        return R.ok().build();
    }

    /**
     * 获取流程模型详情
     *
     * @param modelId 流程模型ID
     * @return 流程模型详情
     */
    @Operation(summary = "获取流程模型详情")
    @GetMapping("/find")
    public R<FlowModelResponse> findModelById(@RequestParam("modelId") String modelId) {
        return R.ok().build(flowModelService.findModelById(modelId));
    }

    /**
     * 分页查询流程模型
     *
     * @param pageQuery 流程模型查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询流程模型")
    @GetMapping("/page")
    public R<PageResponse<FlowModelResponse>> findModelByPage(FlowModelPageQuery pageQuery) {
        return R.ok().build(flowModelService.findModelByPage(pageQuery));
    }

    /**
     * 分页查询流程模型
     *
     * @param pageQuery 流程模型查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询流程模型")
    @GetMapping("/page")
    public R<PageResponse<FlowModelResponse>> findHistoryModelByPage(@RequestParam("modelId") String modelId, FlowModelPageQuery pageQuery) {
        return R.ok().build(flowModelService.findHistoryModelByPage(modelId, pageQuery));
    }

    /**
     * 根据模型id查询 bpmn xml
     *
     * @param modelId 模型id
     * @return bpmn xml
     */
    @Operation(summary = "根据模型id查询 bpmn xml")
    @GetMapping("/bpmn")
    public R<String> queryBpmnXmlById(@RequestParam("modelId") String modelId) {
        return R.ok().build(flowModelService.queryBpmnXmlById(modelId));
    }

}
