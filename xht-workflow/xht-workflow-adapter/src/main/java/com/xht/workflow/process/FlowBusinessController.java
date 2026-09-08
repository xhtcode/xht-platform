package com.xht.workflow.process;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.process.domain.query.FlowBusinessPageQuery;
import com.xht.workflow.process.domain.response.FlowBusinessResponse;
import com.xht.workflow.process.service.IFlowBusinessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述： 流程扩展-流程业务控制器
 *
 * @author xht
 **/
@Tag(name = "流程业务管理", description = "流程扩展-流程业务管理")
@Slf4j
@RestController
@RequestMapping("/workflow/business")
@RequiredArgsConstructor
public class FlowBusinessController {

    private final IFlowBusinessService flowBusinessService;

    /**
     * 获取流程业务详情
     *
     * @param id 流程业务ID
     * @return 流程业务详情
     */
    @Operation(summary = "获取流程业务详情")
    @GetMapping("/get/{id}")
    public R<FlowBusinessResponse> findById(@PathVariable Long id) {
        return R.ok().build(flowBusinessService.findById(id));
    }

    /**
     * 分页查询流程业务
     *
     * @param query 流程业务查询参数
     * @return 流程业务分页信息
     */
    @Operation(summary = "分页查询流程业务")
    @GetMapping("/page")
    public R<PageResponse<FlowBusinessResponse>> findPageList(FlowBusinessPageQuery query) {
        return R.ok().build(flowBusinessService.findPageList(query));
    }

}
