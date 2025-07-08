package com.xht.system.oauth2.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.oauth2.domian.request.SysOauth2ClientFormRequest;
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

    /**
     * 更新OAuth2客户端信息
     *
     * @param formRequest OAuth2客户端修改信息
     * @return 是否成功
     */
    public Boolean updateFormRequest(SysOauth2ClientFormRequest formRequest) {
        LambdaUpdateWrapper<SysOauth2ClientEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(SysOauth2ClientEntity::getClientId, formRequest.getClientId())
                .set(SysOauth2ClientEntity::getClientName, formRequest.getClientName())
                .set(SysOauth2ClientEntity::getClientSecret, formRequest.getClientSecret())
                .set(SysOauth2ClientEntity::getClientIdIssuedAt, formRequest.getClientIdIssuedAt())
                .set(SysOauth2ClientEntity::getClientSecretExpiresAt, formRequest.getClientSecretExpiresAt())
                .set(SysOauth2ClientEntity::getAuthorizationGrantTypes, formRequest.getAuthorizationGrantTypes())
                .set(SysOauth2ClientEntity::getScopes, formRequest.getScopes())
                .set(SysOauth2ClientEntity::getRedirectUris, formRequest.getRedirectUris())
                .set(SysOauth2ClientEntity::getAccessTokenValidity, formRequest.getAccessTokenValidity())
                .set(SysOauth2ClientEntity::getRefreshTokenValidity, formRequest.getRefreshTokenValidity())
                .set(SysOauth2ClientEntity::getAdditionalInformation, formRequest.getAdditionalInformation())
                .set(SysOauth2ClientEntity::getAutoApprove, formRequest.getAutoApprove());
        // @formatter:on
        updateWrapper.eq(SysOauth2ClientEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }
}
