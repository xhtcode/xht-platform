package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.DataBaseQueryRequest;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;
import com.xht.generate.domain.request.ImportTableFormRequest;
import com.xht.generate.domain.response.GenTableInfoResponse;
import com.xht.generate.domain.vo.GenTableColumnVo;
import com.xht.generate.service.IGenTableInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 表信息管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "表信息管理", description = "表信息管理")
@RestController
@RequestMapping("/gen/table/info")
@RequiredArgsConstructor
public class GenTableInfoController {

    private final IGenTableInfoService genTableInfoService;

    /**
     * 导入表
     *
     * @param formRequest 表信息表单请求参数
     * @return 操作结果 true表示成功，false表示失败
     */
    @Operation(summary = "创建表信息")
    @PostMapping("/import")
    public R<Boolean> importTable(@Validated @RequestBody ImportTableFormRequest formRequest) {
        return R.ok(genTableInfoService.importTable(formRequest));
    }

    /**
     * 同步表信息
     *
     * @param tableId 表id
     * @return 操作结果 true表示成功，false表示失败
     */
    @Operation(summary = "同步表信息")
    @PostMapping("/syncTable/{tableId}")
    public R<Boolean> syncTable(@Validated @PathVariable("tableId") String tableId) {
        return R.ok(genTableInfoService.syncTable(tableId));
    }

    /**
     * 根据ID删除表信息
     *
     * @param id 表信息ID
     * @return 操作结果 true表示成功，false表示失败
     */
    @Operation(summary = "根据ID删除表信息")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "表信息ID", required = true) String id) {
        return R.ok(genTableInfoService.removeById(id));
    }

    /**
     * 根据ID更新表信息
     *
     * @param formRequest 表信息更新请求参数
     * @return 操作结果 true表示成功，false表示失败
     */
    @Operation(summary = "根据ID更新表信息")
    @PostMapping("/update")
    public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenTableInfoFormRequest formRequest) {
        return R.ok(genTableInfoService.updateById(formRequest));
    }

    /**
     * 根据ID查询表信息
     *
     * @param id 表信息ID
     * @return 表信息字段信息
     */
    @Operation(summary = "根据ID查询表信息")
    @GetMapping("/get/{id}")
    public R<GenTableColumnVo> findById(@PathVariable @Parameter(description = "表信息ID", required = true) Long id) {
        return R.ok(genTableInfoService.getById(id));
    }

    /**
     * 分页查询已存在的表信息
     *
     * @param queryRequest 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Operation(summary = "分页查询表信息")
    @GetMapping("/exists/page")
    public R<PageResponse<GenTableInfoResponse>> selectExistsPage(GenTableInfoQueryRequest queryRequest) {
        return R.ok(genTableInfoService.selectExistsPage(queryRequest));
    }

    /**
     * 分页查询不存在的表信息
     *
     * @param queryRequest 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Operation(summary = "分页查询表信息")
    @GetMapping("/no/exists/page")
    public R<List<GenTableInfoResponse>> selectNoExistsList(DataBaseQueryRequest queryRequest) {
        return R.ok(genTableInfoService.selectNoExistsList(queryRequest));
    }

}
