package com.xht.platform.controller.system;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.utils.tree.INode;
import com.xht.framework.validation.Groups;
import  com.xht.platform.system.domain.form.SysDeptForm;
import  com.xht.platform.system.domain.query.SysDeptTreeQuery;
import  com.xht.platform.system.domain.response.SysDeptResponse;
import  com.xht.platform.system.enums.DeptStatusEnum;
import  com.xht.platform.system.service.ISysDeptService;
import io.swagger.v3.oas.annotations.Operation;
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
    @BLog(value = "部门管理", description = "")
    @CheckMenu("sys:dept:create")
    @Operation(summary = "创建部门")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDeptForm form) {
        sysDeptService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除部门
     *
     * @param deptId 部门ID
     * @return 统一响应结果
     */
    @BLog(value = "部门管理", description = "")
    @CheckMenu("sys:dept:remove")
    @Operation(summary = "删除部门")
    @PostMapping("/remove/{deptId}")
    public R<Void> removeById(@PathVariable Long deptId) {
        sysDeptService.removeById(deptId);
        return R.ok().build();
    }

    /**
     * 根据ID更新部门
     *
     * @param deptId 部门ID
     * @param form 部门更新请求参数
     * @return 统一响应结果
     */
    @BLog(value = "部门管理", description = "")
    @CheckMenu("sys:dept:updateById")
    @Operation(summary = "更新部门")
    @PostMapping("/update/{deptId}")
    public R<Void> updateById(@PathVariable Long deptId, @Validated(value = {Groups.Update.class}) @RequestBody SysDeptForm form) {
        sysDeptService.updateById(deptId, form);
        return R.ok().build();
    }

    /**
     * 更新部门状态
     *
     * @param deptId     部门ID
     * @param status 部门状态
     * @return 统一响应结果
     */
    @BLog(value = "部门管理", description = "")
    @CheckMenu("sys:dept:updateById")
    @Operation(summary = "更新部门状态")
    @PostMapping("/{deptId}/status/{status}")
    public R<Void> updateStatus(@PathVariable Long deptId, @PathVariable DeptStatusEnum status) {
        sysDeptService.updateStatus(deptId, status);
        return R.ok().build();
    }

    /**
     * 根据ID查询部门
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Operation(summary = "查询详情")
    @GetMapping("/get/{deptId}")
    public R<SysDeptResponse> findById(@PathVariable Long deptId) {
        return R.ok().build(sysDeptService.findById(deptId));
    }

    /**
     * 获取部门树形结构
     *
     * @param treeRequest 部门树形结构请求参数
     * @return 部门树形结构
     */
    @Operation(summary = "获取部门树形结构")
    @GetMapping("/tree")
    public R<List<INode<Long>>> getDeptTree(SysDeptTreeQuery treeRequest) {
        return R.ok().build(sysDeptService.getDeptTree(treeRequest));
    }

}
