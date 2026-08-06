package com.xht.platform.controller.system;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.validation.Groups;
import  com.xht.platform.system.domain.form.SysPostForm;
import  com.xht.platform.system.domain.query.SysPostQuery;
import  com.xht.platform.system.domain.response.SysPostResponse;
import  com.xht.platform.system.service.ISysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @BLog(value = "部门岗位管理", description = "")
    @CheckMenu("sys:post:create")
    @Operation(summary = "创建部门岗位", description = "根据提供的请求参数创建一个新的部门岗位")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysPostForm form) {
        sysDeptPostService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除部门岗位
     *
     * @param postId 部门岗位ID
     * @return 统一响应结果
     */
    @BLog(value = "部门岗位管理", description = "")
    @CheckMenu("sys:post:remove")
    @Operation(summary = "根据ID删除部门岗位", description = "根据提供的部门岗位ID删除部门岗位")
    @PostMapping("/remove/{postId}")
    public R<Void> removeById(@PathVariable Long postId) {
        sysDeptPostService.removeById(postId);
        return R.ok().build();
    }

    /**
     * 根据ID更新部门岗位
     *
     * @param postId 部门岗位ID
     * @param form 部门岗位更新请求参数
     * @return 统一响应结果
     */
    @BLog(value = "部门岗位管理", description = "")
    @CheckMenu("sys:post:updateById")
    @Operation(summary = "根据ID更新部门岗位", description = "根据提供的部门岗位更新请求参数更新部门岗位")
    @PostMapping("/update/{postId}")
    public R<Void> updateById(@PathVariable Long postId, @Validated(value = {Groups.Update.class}) @RequestBody SysPostForm form) {
        sysDeptPostService.updateById(postId, form);
        return R.ok().build();
    }

    /**
     * 根据ID查询部门岗位
     *
     * @param postId 部门岗位ID
     * @return 部门岗位信息
     */
    @Operation(summary = "查询详情", description = "根据提供的部门岗位ID查询部门岗位信息")
    @GetMapping("/get/{postId}")
    public R<SysPostResponse> findById(@PathVariable @Parameter(description = "部门岗位ID", required = true) Long postId) {
        return R.ok().build(sysDeptPostService.findById(postId));
    }

    /**
     * 分页查询部门岗位
     *
     * @param query 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    @Operation(summary = "分页查询", description = "根据提供的查询请求参数分页查询部门岗位信息")
    @GetMapping("/page")
    public R<PageResponse<SysPostResponse>> findPageList(@Valid SysPostQuery query) {
        return R.ok().build(sysDeptPostService.findPageList(query));
    }

}
