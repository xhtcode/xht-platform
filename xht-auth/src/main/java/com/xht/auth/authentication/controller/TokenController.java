package com.xht.auth.authentication.controller;

import com.xht.auth.authentication.domain.response.TokenUserInfoResponse;
import com.xht.auth.authentication.service.ITokenService;
import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.domain.R;
import com.xht.framework.oauth2.token.form.TokenForm;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.security.utils.Oauth2Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Token 控制器 ,提供令牌的注销、检查和删除功能
 *
 * @author xht
 */
@Tag(name = "令牌管理", description = "提供令牌的注销、检查和删除功能")
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final ITokenService tokenService;

    /**
     * 获取当前登录用户信息
     * 该方法会检查用户是否已登录，如果已登录则返回用户信息，否则返回错误响应
     *
     * @return R<TokenUserInfo> 操作结果，已登录返回用户信息，未登录返回错误信息
     */
    @GetMapping("/info")
    public R<TokenUserInfoResponse> getUserInfo() {
        // 查看所有用户
        boolean login = SecurityUtils.isLogin();
        if (login) {
            return R.ok().build(tokenService.getTokenUserInfo(SecurityUtils.getUserId()));
        }
        return R.error().build();
    }

    /**
     * 注销并删除令牌
     *
     * @param request 请求对象
     * @return R<Void> 操作结果，成功返回 OK，失败返回错误信息
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/logout")
    @ResponseBody
    @Operation(summary = "注销并删除令牌", description = "注销并删除令牌")
    public R<Void> logout(HttpServletRequest request) {
        tokenService.removeToken(Oauth2Utils.getBearerAuthorization(request.getHeader(HttpConstants.Header.AUTHORIZATION.getValue())));
        return R.ok().msg("注销成功").build();
    }

    /**
     * 检查令牌有效性
     *
     * @param request 请求对象
     * @return R<Void> 操作结果，令牌有效返回 OK，无效返回 TOKEN_EXPIRED 错误
     */
    @GetMapping("/check_token")
    @Operation(summary = "检查令牌有效性", description = "检查令牌有效性")
    public R<Void> checkToken(HttpServletRequest request) {
        tokenService.checkToken(Oauth2Utils.getBearerAuthorization(request.getHeader(HttpConstants.Header.AUTHORIZATION.getValue())));
        return R.ok().build();
    }


    /**
     * 删除令牌
     * 该方法会清除令牌缓存、删除授权信息并发布注销成功事件
     *
     * @param token 待删除的令牌
     * @return R<Void> 操作结果，成功返回 OK
     */
    @PostMapping("/remove")
    @Operation(summary = "删除令牌", description = "删除令牌")
    public R<Void> removeToken(@RequestBody @Valid TokenForm token) {
        tokenService.removeToken(token.getAccessToken());
        return R.ok().build();
    }

}
