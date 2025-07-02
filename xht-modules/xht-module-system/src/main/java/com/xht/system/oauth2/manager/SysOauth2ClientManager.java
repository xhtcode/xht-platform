package com.xht.system.oauth2.manager;

import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.oauth2.mapper.SysOauth2ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysOauth2ClientManager extends BasicManager<SysOauth2ClientMapper, SysOauth2ClientEntity> {
}
