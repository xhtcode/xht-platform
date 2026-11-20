package com.xht.platform.controller.user;

import com.xht.framework.common.domain.R;
import com.xht.framework.utils.tree.INode;
import com.xht.platform.login.domain.response.UserLoginLogResponse;
import com.xht.platform.system.domain.vo.SysUserVO;
import com.xht.platform.user.IUserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户中心控制器
 * 处理当前登录用户相关接口：头像上传、个人信息、菜单路由、登录日志等
 *
 * @author xht
 **/
@Tag(name = "用户中心")
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final IUserProfileService userProfileService;

    /**
     * 用户头像上传
     *
     * @param avatar 头像文件
     * @return 头像访问地址
     */
    @Operation(summary = "用户头像上传", description = "上传用户头像文件，返回头像URL地址")
    @PostMapping("/upload/avatar")
    public R<String> uploadUserAvatar(HttpServletRequest request, @RequestParam("avatar") MultipartFile avatar) {
        return R.ok().build(userProfileService.uploadUserAvatar(request, avatar));
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息VO（昵称、手机号、头像、角色等）
     */
    @Operation(summary = "获取用户信息", description = "获取当前登录账号基础信息")
    @GetMapping("/profile/info")
    public R<SysUserVO> getUserProfileInfo() {
        return R.ok().build(userProfileService.getUserProfileInfo());
    }

    /**
     * 获取当前用户路由菜单
     *
     * @return 菜单路由树节点集合，用于前端渲染侧边栏菜单
     */
    @Operation(summary = "获取路由菜单", description = "根据当前用户权限生成前端可渲染的菜单路由树")
    @GetMapping("/profile/routers")
    public R<List<INode<Long>>> getUserProfileRouters() {
        return R.ok().build(userProfileService.getUserProfileRouters());
    }

    /**
     * 获取当前登录用户登录日志
     *
     * @return 登录日志信息（示例接口，待业务完善）
     */
    @Operation(summary = "获取登录日志", description = "查询当前用户登录记录日志")
    @GetMapping("/profile/login")
    public R<List<UserLoginLogResponse>> getUserProfileLoginLog() {
        return R.ok().build(userProfileService.getUserProfileLoginLog());
    }

}
