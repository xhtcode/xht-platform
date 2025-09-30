package com.xht.system.modules.dept.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import com.xht.system.modules.dept.domain.request.SysDeptForm;
import com.xht.system.modules.dept.domain.request.SysDeptTreeQuery;
import com.xht.system.modules.dept.domain.response.SysDeptResp;
import com.xht.system.modules.dept.service.ISysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门 Controller
 *
 * @author xht
 */
@Tag(name = "部门管理")
@RestController
@RequestMapping("/sys/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final ISysDeptService sysDeptService;

    /**
     * 创建部门
     *
     * @param form 部门表单请求参数
     * @return 统一响应结果
     */
    @PostMapping("/create")
    @Operation(summary = "创建部门")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDeptForm form) {
        sysDeptService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除部门
     *
     * @param id 部门ID
     * @return 统一响应结果
     */
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除部门")
    @Parameter(name = "id", description = "部门ID")
    public R<Void> removeById(@PathVariable Long id) {
        sysDeptService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID更新部门
     *
     * @param form 部门更新请求参数
     * @return 统一响应结果
     */
    @PostMapping("/update")
    @Operation(summary = "更新部门")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysDeptForm form) {
        sysDeptService.updateById(form);
        return R.ok();
    }

    /**
     * 更新部门状态
     *
     * @param id     部门ID
     * @param status 部门状态
     * @return 统一响应结果
     */
    @PostMapping("/{id}/status/{status}")
    @Operation(summary = "更新部门状态")
    @Parameter(name = "id", description = "部门ID")
    @Parameter(name = "status", description = "部门状态")
    public R<Void> updateStatus(@PathVariable Long id, @PathVariable DeptStatusEnums status) {
        sysDeptService.updateStatus(id, status);
        return R.ok();
    }

    /**
     * 根据ID查询部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "查询部门详情")
    @Parameter(name = "id", description = "部门ID")
    public R<SysDeptResp> findById(@PathVariable Long id) {
        return R.ok(sysDeptService.findById(id));
    }

    /**
     * 获取部门树形结构
     *
     * @param treeRequest 部门树形结构请求参数
     * @return 部门树形结构
     */
    @GetMapping("/tree")
    @Operation(summary = "获取部门树形结构")
    public R<List<INode<Long>>> getDeptTree(SysDeptTreeQuery treeRequest) {
        return R.ok(sysDeptService.getDeptTree(treeRequest));
    }
}