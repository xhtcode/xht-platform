package com.xht.system.modules.dept.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.dept.domain.request.SysPostFormRequest;
import com.xht.system.modules.dept.domain.request.SysPostQueryRequest;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.system.modules.dept.service.ISysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门岗位管理
 *
 * @author xht
 **/
@Tag(name = "部门岗位管理")
@RestController
@RequestMapping("/sys/dept/post")
@RequiredArgsConstructor
public class SysPostController {

    private final ISysPostService SysDeptPostService;

    /**
     * 创建部门岗位
     *
     * @param formRequest 部门岗位表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建部门岗位", description = "根据提供的请求参数创建一个新的部门岗位")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody SysPostFormRequest formRequest) {
        return R.ok(SysDeptPostService.create(formRequest));
    }

    /**
     * 根据ID删除部门岗位
     *
     * @param id 部门岗位ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除部门岗位", description = "根据提供的部门岗位ID删除部门岗位")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable Long id) {
        return R.ok(SysDeptPostService.removeById(id));
    }

    /**
     * 根据ID删除部门岗位
     *
     * @param ids 部门岗位ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除部门岗位", description = "根据提供的部门岗位ID删除部门岗位")
    @PostMapping("/delete/")
    public R<Boolean> removeByIds(@RequestBody List<Long> ids) {
        return R.ok(SysDeptPostService.removeByIds(ids));
    }

    /**
     * 根据ID更新部门岗位
     *
     * @param formRequest 部门岗位更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新部门岗位", description = "根据提供的部门岗位更新请求参数更新部门岗位")
    @PostMapping("/update")
    public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysPostFormRequest formRequest) {
        return R.ok(SysDeptPostService.updateById(formRequest));
    }

    /**
     * 根据ID查询部门岗位
     *
     * @param id 部门岗位ID
     * @return 部门岗位信息
     */
    @Operation(summary = "根据ID查询部门岗位", description = "根据提供的部门岗位ID查询部门岗位信息")
    @GetMapping("/get/{id}")
    public R<SysPostResponse> findById(@PathVariable @Parameter(description = "部门岗位ID", required = true) Long id) {
        return R.ok(SysDeptPostService.getById(id));
    }

    /**
     * 分页查询部门岗位
     *
     * @param queryRequest 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    @Operation(summary = "分页查询部门岗位", description = "根据提供的查询请求参数分页查询部门岗位信息")
    @GetMapping("/page")
    public R<PageResponse<SysPostResponse>> selectPage(@Valid SysPostQueryRequest queryRequest) {
        return R.ok(SysDeptPostService.selectPage(queryRequest));
    }


}
