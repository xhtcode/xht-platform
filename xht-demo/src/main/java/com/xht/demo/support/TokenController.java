package com.xht.demo.support;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token 断点
 *
 * @author xht
 **/
@RestController
@RequiredArgsConstructor
public class TokenController {

    @GetMapping("/aaaa")
    public Object a() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
