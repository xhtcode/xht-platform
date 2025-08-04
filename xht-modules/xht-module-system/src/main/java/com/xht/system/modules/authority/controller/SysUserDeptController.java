package com.xht.system.modules.authority.controller;

import com.xht.framework.core.domain.R;
import com.xht.system.modules.authority.domain.request.UserBindDeptPostRequest;
import com.xht.system.modules.authority.service.IUserDeptService;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
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
public class SysUserDeptController {

    private final IUserDeptService userDeptService;

    /**
     * 用户绑定部门岗位信息
     *
     * @param bindRequest 用户绑定部门岗位请求参数
     * @return true成功，false失败
     */
    @Operation(description = "用户绑定部门岗位信息", summary = "用户绑定部门岗位信息")
    @PostMapping("/bind")
    public R<Boolean> deptBindUser(@RequestBody UserBindDeptPostRequest bindRequest) {
        return R.ok(userDeptService.userBindDept(bindRequest));
    }

    /**
     * 获取已绑定的用户信息
     *
     * @param deptId 部门ID，可选参数，用于筛选指定部门下的已绑定用户
     * @return 返回部门下已绑定的用户信息列表，封装在R对象中
     */
    @Operation(description = "获取已绑定的用户信息", summary = "获取已绑定的用户信息")
    @GetMapping("/getBindUser")
    public R<List<UserSimpleVo>> getBindUser(@RequestParam(value = "deptId", required = false) Long deptId) {
        return R.ok(userDeptService.getBindUserByDeptId(deptId));
    }


}
