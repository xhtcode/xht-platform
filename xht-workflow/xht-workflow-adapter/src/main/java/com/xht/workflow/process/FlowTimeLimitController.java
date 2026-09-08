package com.xht.workflow.process;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.process.domain.query.FlowTimeLimitPageQuery;
import com.xht.workflow.process.domain.response.FlowTimeLimitResponse;
import com.xht.workflow.process.service.IFlowTimeLimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述： 流程扩展-流程时限控制器
 *
 * @author xht
 **/
@Tag(name = "流程时限管理", description = "流程扩展-流程时限管理")
@Slf4j
@RestController
@RequestMapping("/workflow/business/time-limit")
@RequiredArgsConstructor
public class FlowTimeLimitController {

    private final IFlowTimeLimitService flowTimeLimitService;

    /**
     * 获取流程时限详情
     *
     * @param id 流程时限ID
     * @return 流程时限详情
     */
    @Operation(summary = "获取流程时限详情")
    @GetMapping("/get/{id}")
    public R<FlowTimeLimitResponse> findById(@PathVariable Long id) {
        return R.ok().build(flowTimeLimitService.findById(id));
    }

    /**
     * 分页查询流程时限
     *
     * @param query 流程时限查询参数
     * @return 流程时限分页信息
     */
    @Operation(summary = "分页查询流程时限")
    @GetMapping("/page")
    public R<PageResponse<FlowTimeLimitResponse>> findPageList(FlowTimeLimitPageQuery query) {
        return R.ok().build(flowTimeLimitService.findPageList(query));
    }

}
