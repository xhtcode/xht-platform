package com.xht.system.dept.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.tree.INode;
import com.xht.system.dept.common.enums.DeptStatusEnums;
import com.xht.system.dept.domain.request.SysDeptFormRequest;
import com.xht.system.dept.domain.request.SysDeptQueryTreeRequest;
import com.xht.system.dept.domain.response.SysDeptResponse;
import com.xht.system.dept.service.ISysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
     * @param formRequest 部门创建请求参数
     * @return 操作结果
     */
    @PostMapping("/add")
    @Operation(summary = "创建部门")
    public R<Boolean> create(@RequestBody SysDeptFormRequest formRequest) {
        return R.ok(sysDeptService.create(formRequest));
    }

    /**
     * 根据ID删除部门
     *
     * @param id 部门ID
     * @return 操作结果
     */
    @PostMapping("/delete/{id}")
    @Operation(summary = "删除部门")
    @Parameter(name = "id", description = "部门ID")
    public R<Boolean> removeById(@PathVariable Long id) {
        return R.ok(sysDeptService.removeById(id));
    }

    /**
     * 根据ID更新部门
     *
     * @param formRequest 部门更新请求参数
     * @return 操作结果
     */
    @PostMapping("/update")
    @Operation(summary = "更新部门")
    public R<Boolean> updateById(@RequestBody SysDeptFormRequest formRequest) {
        return R.ok(sysDeptService.updateById(formRequest));
    }

    /**
     * 更新部门状态
     *
     * @param id     部门ID
     * @param status 部门状态
     * @return 操作结果
     */
    @PostMapping("/{id}/status/{status}")
    @Operation(summary = "更新部门状态")
    @Parameter(name = "id", description = "部门ID")
    @Parameter(name = "status", description = "部门状态")
    public R<Boolean> updateStatus(@PathVariable Long id, @PathVariable DeptStatusEnums status) {
        return R.ok(sysDeptService.updateStatus(id, status));
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
    public R<SysDeptResponse> getById(@PathVariable Long id) {
        return R.ok(sysDeptService.getById(id));
    }

    /**
     * 获取部门树形结构
     *
     * @param treeRequest 部门树形结构请求参数
     * @return 部门树形结构
     */
    @GetMapping("/tree")
    @Operation(summary = "获取部门树形结构")
    public R<List<INode<Long>>> getDeptTree(SysDeptQueryTreeRequest treeRequest) {
        return R.ok(sysDeptService.getDeptTree(treeRequest));
    }
}