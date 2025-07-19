package com.xht.system.modules.user.controller;

import com.xht.framework.core.domain.R;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.user.domain.request.DeptBindUserRequest;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
import com.xht.system.modules.user.service.IUserDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户部门管理
 *
 * @author xht
 **/
@Tag(name = "用户部门管理")
@Slf4j
@RestController
@RequestMapping("/sys/user/dept")
@RequiredArgsConstructor
public class UserDeptController {

    private final IUserDeptService userDeptService;

    /**
     * 绑定部门
     *
     * @param bindRequest 绑定请求
     * @return 成功或失败
     */
    @Operation(description = "绑定部门", summary = "绑定部门")
    @PostMapping("/bind")
    public R<Boolean> deptBindUser(DeptBindUserRequest bindRequest) {
        return R.ok(userDeptService.userBindDept(bindRequest.getDeptId(), bindRequest.getUserIds()));
    }

    /**
     * 获取已绑定的用户信息
     */
    @Operation(description = "获取已绑定的用户信息", summary = "获取已绑定的用户信息")
    @GetMapping("/getBindUser")
    public R<List<UserSimpleVo>> getBindUser(@RequestParam(value = "deptId", required = false) Long deptId) {
        return R.ok(userDeptService.getBindUserByDeptId(deptId));
    }

    /**
     * 根据用户id获取部门信息
     */
    @Operation(description = "根据用户id获取部门信息", summary = "根据用户id获取部门信息")
    @PostMapping("/getDeptByUserId/{userId}")
    public R<SysDeptPostVo> getDeptByUserId(@PathVariable("userId") Long userId) {
        return R.ok(userDeptService.getDeptByUserId(userId));
    }
}
