package com.xht.auth.security.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.auth.redis.repository.Oauth2AuthorizationRepository;
import com.xht.framework.openfeign.annotation.NoAuthentication;
import com.xht.framework.security.annotation.InnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token 断点
 *
 * @author xht
 **/
@RequiredArgsConstructor
@RestController
public class TokenController {

    private Oauth2AuthorizationRepository authorizationRepository;

    @GetMapping("/aaaa")
    @NoAuthentication
    @InnerAuth
    public R<Object> a(PageBasicQuery pageRequest) {
        return null;
    }
}
