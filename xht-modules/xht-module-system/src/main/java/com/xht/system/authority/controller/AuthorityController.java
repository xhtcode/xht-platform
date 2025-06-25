package com.xht.system.authority.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.tree.INode;
import com.xht.system.authority.domain.vo.AuthorityUserVO;
import com.xht.system.authority.service.IAuthorityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xht
 **/
@Tag(name = "权限模块")
@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final IAuthorityService authorityService;


    /**
     * sys/user/profile/info
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/user/profile/info")
    public R<AuthorityUserVO> getUserProfileInfo() {
        return R.ok(authorityService.getUserProfileInfo());
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @Operation(summary = "获取路由信息")
    @GetMapping("/user/profile/getRouters")
    public R<List<INode<Long>>> getRouters() {
        return R.ok(authorityService.getRouters());
    }


}
