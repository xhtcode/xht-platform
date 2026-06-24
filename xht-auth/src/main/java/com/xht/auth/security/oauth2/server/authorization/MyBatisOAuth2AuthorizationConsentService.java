package com.xht.auth.security.oauth2.server.authorization;

import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.auth.consent.dao.ISysOauth2AuthorizationConsentDao;
import com.xht.auth.consent.entity.SysOauth2AuthorizationConsentEntity;
import com.xht.auth.security.oauth2.server.authorization.client.Oauth2RegisteredClientRepository;
import com.xht.framework.security.core.device.provider.DeviceCodeProvider;
import com.xht.framework.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 授权确认信息
 *
 * @author xht
 **/
@Slf4j
@Service
public class MyBatisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final ISysOauth2AuthorizationConsentDao iSysOauth2AuthorizationConsentDao;

    private final Oauth2RegisteredClientRepository oauth2RegisteredClientRepository;

    private final DeviceCodeProvider deviceCodeProvider;

    public MyBatisOAuth2AuthorizationConsentService(ISysOauth2AuthorizationConsentDao iSysOauth2AuthorizationConsentDao, Oauth2RegisteredClientRepository oauth2RegisteredClientRepository, XhtOauth2Properties xhtOauth2Properties) {
        this.iSysOauth2AuthorizationConsentDao = iSysOauth2AuthorizationConsentDao;
        this.oauth2RegisteredClientRepository = oauth2RegisteredClientRepository;
        this.deviceCodeProvider = new DeviceCodeProvider(xhtOauth2Properties.getDeviceCode().getSalt());
    }

    /**
     * 保存 {@link OAuth2AuthorizationConsent}。
     *
     * @param authorizationConsent 要保存的 {@link OAuth2AuthorizationConsent}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "授权确认信息 不能是空的");
        HttpServletRequest servletRequest = ServletUtil.getHttpServletRequest();
        String deviceCode = deviceCodeProvider.generateDeviceCode(servletRequest);
        SysOauth2AuthorizationConsentEntity entity = new SysOauth2AuthorizationConsentEntity();
        String registeredClientId = authorizationConsent.getRegisteredClientId();
        RegisteredClient registeredClient = oauth2RegisteredClientRepository.findByClientId(registeredClientId);
        entity.setRegisteredClientId(registeredClientId);
        if (Objects.nonNull(registeredClient)) {
            entity.setRegisteredClientName(registeredClient.getClientName());
        }
        entity.setPrincipalName(authorizationConsent.getPrincipalName());
        Set<GrantedAuthority> authorities = authorizationConsent.getAuthorities();
        Set<String> dbAuthorities = new HashSet<>();
        if (!CollectionUtils.isEmpty(authorities)) {
            for (GrantedAuthority authority : authorities) {
                dbAuthorities.add(authority.getAuthority());
            }
        }
        entity.setAuthorities(dbAuthorities);
        entity.setDeviceCode(deviceCode);
        iSysOauth2AuthorizationConsentDao.save(entity);
    }

    /**
     * 删除 {@link OAuth2AuthorizationConsent}。
     *
     * @param authorizationConsent 要删除的 {@link OAuth2AuthorizationConsent}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "授权确认信息 不能是空的");
        iSysOauth2AuthorizationConsentDao.removeByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    /**
     * 根据提供的 {@code registeredClientId} 和 {@code principalName} 查找并返回
     * 对应的 {@link OAuth2AuthorizationConsent}，如果未找到则返回 {@code null}。
     *
     * @param registeredClientId 注册客户端的id，对应于 {@link RegisteredClient}
     * @param principalName      主体名称，即 {@link Principal} 的名字
     * @return 如果找到了匹配的 {@link OAuth2AuthorizationConsent} 则返回它，否则返回 {@code null}
     */
    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.hasText(registeredClientId, "客户端的id 不能是空的");
        Assert.hasText(principalName, "主体名称 不能是空的");
        HttpServletRequest servletRequest = ServletUtil.getHttpServletRequest();
        String deviceCode = deviceCodeProvider.generateDeviceCode(servletRequest);
        List<SysOauth2AuthorizationConsentEntity> entityList = iSysOauth2AuthorizationConsentDao
                .findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName, deviceCode);
        if (!CollectionUtils.isEmpty(entityList)) {
            SysOauth2AuthorizationConsentEntity entity = entityList.get(0);
            OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(entity.getRegisteredClientId(), entity.getPrincipalName());
            Set<String> authorities = entity.getAuthorities();
            if (!CollectionUtils.isEmpty(authorities)) {
                for (String authority : authorities) {
                    builder.authority(new SimpleGrantedAuthority(authority));
                }
            }
            return builder.build();
        }
        return null;
    }

}
