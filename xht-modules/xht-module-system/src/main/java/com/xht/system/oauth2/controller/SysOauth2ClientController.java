package com.xht.system.oauth2.controller;

import com.xht.system.oauth2.service.ISysOauth2ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Tag(name = "OAuth2客户端管理")
@RestController
@RequestMapping("/sys/oauth2")
@RequiredArgsConstructor
public class SysOauth2ClientController {

    private final ISysOauth2ClientService sysOauth2ClientService;
}
