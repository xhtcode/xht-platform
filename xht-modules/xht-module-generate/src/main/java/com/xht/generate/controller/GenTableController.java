package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.form.ImportTableBasicForm;
import com.xht.generate.domain.form.TableColumnForm;
import com.xht.generate.domain.query.DataBaseBasicQuery;
import com.xht.generate.domain.query.GenTableInfoBasicQuery;
import com.xht.generate.domain.response.GenTableResponse;
import com.xht.generate.domain.vo.TableColumnVo;
import com.xht.generate.service.IGenTableService;
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
public class GenTableController {

    private final IGenTableService genTableInfoService;

    /**
     * 导入表
     *
     * @param form 表信息表单请求参数
     * @return 统一响应结果 true表示成功，false表示失败
     */
    @Operation(summary = "创建表信息")
    @PostMapping("/import")
    public R<Void> importTable(@Validated @RequestBody ImportTableBasicForm form) {
        genTableInfoService.importTable(form);
        return R.ok();
    }

    /**
     * 同步表信息
     *
     * @param tableId 表id
     * @return 统一响应结果 true表示成功，false表示失败
     */
    @Operation(summary = "同步表信息")
    @PostMapping("/syncTable/{tableId}")
    public R<Void> syncTable(@Validated @PathVariable("tableId") Long tableId) {
        genTableInfoService.syncTable(tableId);
        return R.ok();
    }

    /**
     * 根据ID删除表信息
     *
     * @param tableId 表信息ID
     * @return 统一响应结果 true表示成功，false表示失败
     */
    @Operation(summary = "根据ID删除表信息")
    @PostMapping("/remove/{tableId}")
    public R<Void> removeById(@PathVariable @Parameter(description = "表信息ID", required = true) Long tableId) {
        genTableInfoService.removeById(tableId);
        return R.ok();
    }

    /**
     * 根据ID更新表信息
     *
     * @param form 表信息更新请求参数
     * @return 统一响应结果 true表示成功，false表示失败
     */
    @Operation(summary = "根据ID更新表信息")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody TableColumnForm form) {
        genTableInfoService.updateById(form);
        return R.ok();
    }

    /**
     * 根据ID查询表信息
     *
     * @param id 表信息ID
     * @return 表信息字段信息
     */
    @Operation(summary = "根据ID查询表信息")
    @GetMapping("/get/{id}")
    public R<TableColumnVo> findById(@PathVariable @Parameter(description = "表信息ID", required = true) Long id) {
        return R.ok(genTableInfoService.findById(id));
    }

    /**
     * 分页查询已存在的表信息
     *
     * @param query 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Operation(summary = "分页查询表信息")
    @GetMapping("/exists/page")
    public R<PageResponse<GenTableResponse>> selectExistsPage(GenTableInfoBasicQuery query) {
        return R.ok(genTableInfoService.selectExistsPage(query));
    }

    /**
     * 分页查询不存在的表信息
     *
     * @param query 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Operation(summary = "分页查询表信息")
    @GetMapping("/no/exists/page")
    public R<List<GenTableResponse>> selectNoExistsList(DataBaseBasicQuery query) {
        return R.ok(genTableInfoService.selectNoExistsList(query));
    }

}
