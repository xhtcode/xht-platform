package com.xht.system.oauth2.service.impl;

import com.xht.system.oauth2.converter.SysOauth2ClientConverter;
import com.xht.system.oauth2.manager.SysOauth2ClientManager;
import com.xht.system.oauth2.service.ISysOauth2ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOauth2ClientServiceImpl implements ISysOauth2ClientService {

    private final SysOauth2ClientManager sysOauth2ClientManager;

    private final SysOauth2ClientConverter sysOauth2ClientConverter;

}
