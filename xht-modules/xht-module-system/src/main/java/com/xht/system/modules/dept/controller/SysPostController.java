package com.xht.system.modules.dept.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.dept.domain.request.SysPostForm;
import com.xht.system.modules.dept.domain.request.SysPostQuery;
import com.xht.system.modules.dept.domain.response.SysPostResp;
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

    private final ISysPostService sysDeptPostService;

    /**
     * 创建部门岗位
     *
     * @param form 部门岗位表单请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "创建部门岗位", description = "根据提供的请求参数创建一个新的部门岗位")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysPostForm form) {
        sysDeptPostService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除部门岗位
     *
     * @param id 部门岗位ID
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID删除部门岗位", description = "根据提供的部门岗位ID删除部门岗位")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable Long id) {
        sysDeptPostService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID删除部门岗位
     *
     * @param ids 部门岗位ID
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID删除部门岗位", description = "根据提供的部门岗位ID删除部门岗位")
    @PostMapping("/remove/")
    public R<Void> removeByIds(@RequestBody List<Long> ids) {
        sysDeptPostService.removeByIds(ids);
        return R.ok();
    }

    /**
     * 根据ID更新部门岗位
     *
     * @param form 部门岗位更新请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID更新部门岗位", description = "根据提供的部门岗位更新请求参数更新部门岗位")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysPostForm form) {
        sysDeptPostService.updateById(form);
        return R.ok();
    }

    /**
     * 根据ID查询部门岗位
     *
     * @param id 部门岗位ID
     * @return 部门岗位信息
     */
    @Operation(summary = "根据ID查询部门岗位", description = "根据提供的部门岗位ID查询部门岗位信息")
    @GetMapping("/get/{id}")
    public R<SysPostResp> findById(@PathVariable @Parameter(description = "部门岗位ID", required = true) Long id) {
        return R.ok(sysDeptPostService.findById(id));
    }

    /**
     * 分页查询部门岗位
     *
     * @param query 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    @Operation(summary = "分页查询部门岗位", description = "根据提供的查询请求参数分页查询部门岗位信息")
    @GetMapping("/page")
    public R<PageResponse<SysPostResp>>findPageList(@Valid SysPostQuery query) {
        return R.ok(sysDeptPostService.findPageList(query));
    }


}
