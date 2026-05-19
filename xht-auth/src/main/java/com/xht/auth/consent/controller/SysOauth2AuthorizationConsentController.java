package com.xht.auth.consent.controller;

import com.xht.auth.consent.domain.response.SysOauth2AuthorizationConsentResponse;
import com.xht.auth.consent.service.ISysOauth2AuthorizationConsentService;
import com.xht.framework.core.domain.R;
import com.xht.framework.oauth2.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 默认授权确认信息控制类
 *
 * @author xht
 **/
@Tag(name = "默认授权确认信息控制类")
@RestController
@RequestMapping("/sys/oauth2/consent")
@RequiredArgsConstructor
public class SysOauth2AuthorizationConsentController {

    private final ISysOauth2AuthorizationConsentService sysOauth2AuthorizationConsentService;

    /**
     * 根据用户名查询授权确认信息
     *
     * @return 授权确认信息
     */
    @GetMapping("/info")
    public R<List<SysOauth2AuthorizationConsentResponse>> findByUserName() {
        String userName = SecurityUtils.getUserName();
        return R.ok().build(sysOauth2AuthorizationConsentService.findByUserName(userName));
    }

    /**
     * 根据客户端id删除拥有的授权确认信息
     *
     * @param registeredClientId 注册客户端id
     * @return 删除结果
     */
    @PostMapping("/remove/possess/{registeredClientId}")
    public R<Void> removeMyByRegisteredClientId(@PathVariable("registeredClientId") String registeredClientId) {
        String userName = SecurityUtils.getUserName();
        sysOauth2AuthorizationConsentService.removeByRegisteredClientIdAndPrincipalName(userName, registeredClientId);
        return R.ok().build();
    }

}
