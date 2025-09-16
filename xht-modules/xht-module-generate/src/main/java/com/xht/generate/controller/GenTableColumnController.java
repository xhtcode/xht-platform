package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.generate.domain.response.GenTableColumnResponse;
import com.xht.generate.service.IGenTableColumnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字段信息管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "字段信息管理", description = "字段信息管理")
@RestController
@RequestMapping("/gen/column/info")
@RequiredArgsConstructor
public class GenTableColumnController {

    private final IGenTableColumnService genColumnInfoService;

    /**
     * 根据ID查询字段信息
     *
     * @param id 字段信息ID
     * @return 字段信息信息
     */
    @Operation(summary = "根据ID查询字段信息", description = "根据提供的字段信息ID查询字段信息信息")
    @GetMapping("/get/{id}")
    public R<GenTableColumnResponse> findById(@PathVariable @Parameter(description = "字段信息ID", required = true) Long id) {
        return R.ok(genColumnInfoService.getById(id));
    }

    /**
     * 根据表id查询字段信息
     *
     * @param tableId 表id
     * @return 字段信息分页信息
     */
    @Operation(summary = "根据表id查询字段信息", description = "根据表id查询字段信息")
    @GetMapping("/list/{tableId}")
    public R<List<GenTableColumnResponse>> listByTableId(@PathVariable("tableId") String tableId) {
        return R.ok(genColumnInfoService.listByTableId(tableId));
    }

}

