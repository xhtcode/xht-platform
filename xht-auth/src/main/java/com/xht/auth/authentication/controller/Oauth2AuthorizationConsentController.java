package com.xht.auth.authentication.controller;

import com.xht.auth.authentication.domain.vo.Oauth2ConsentVo;
import com.xht.auth.authentication.service.IOauth2AuthorizationConsentService;
import com.xht.framework.core.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xht
 **/
@Slf4j
@RequestMapping("/oauth2/authorization")
@RestController
@RequiredArgsConstructor
public class Oauth2AuthorizationConsentController {

    private final IOauth2AuthorizationConsentService oauth2AuthorizationConsentService;

    @RequestMapping("/consent/info")
    public R<Oauth2ConsentVo> getConsentInfo(
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
            @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
            @RequestParam(OAuth2ParameterNames.STATE) String state,
            @RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) String userCode) {
        return R.ok().build(oauth2AuthorizationConsentService.getConsentInfo(scope, clientId, state, userCode));
    }
}
