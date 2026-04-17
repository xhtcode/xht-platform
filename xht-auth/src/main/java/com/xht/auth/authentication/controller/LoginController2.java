package com.xht.auth.authentication.controller;

import cn.hutool.core.util.IdUtil;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.core.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @author xht
 **/
@RestController
public class LoginController2 {
    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private RedisRepository redisRepository;


    @IgnoreAuth(aop = false)
    @PostMapping("/login222")
    public R<Map<String, Object>> login() {
        redisRepository.set(IdUtil.simpleUUID(), "admin");
        return R.ok().build();
    }

}
