package com.xht.auth.consent.service.impl;

import com.xht.auth.consent.converter.SysOauth2AuthorizationConsentConverter;
import com.xht.auth.consent.dao.ISysOauth2AuthorizationConsentDao;
import com.xht.auth.consent.domain.response.SysOauth2AuthorizationConsentResponse;
import com.xht.auth.consent.entity.SysOauth2AuthorizationConsentEntity;
import com.xht.auth.consent.service.ISysOauth2AuthorizationConsentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 默认授权确认信息服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOauth2AuthorizationConsentServiceImpl implements ISysOauth2AuthorizationConsentService {

    private final ISysOauth2AuthorizationConsentDao sysOauth2AuthorizationConsentDao;

    private final SysOauth2AuthorizationConsentConverter sysOauth2AuthorizationConsentConverter;

    /**
     * 根据用户名查询授权确认信息
     *
     * @param userName 用户名
     * @return 授权确认信息
     */
    @Override
    public List<SysOauth2AuthorizationConsentResponse> findByUserName(String userName) {
        List<SysOauth2AuthorizationConsentEntity> entityList = sysOauth2AuthorizationConsentDao.findByPrincipalName(userName);
        return sysOauth2AuthorizationConsentConverter.toResponse(entityList);
    }

    /**
     * 根据注册客户端id删除授权确认信息
     *
     * @param registeredClientId 注册客户端id
     * @param principalName      主体名称
     */
    @Override
    public void removeByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        sysOauth2AuthorizationConsentDao.removeByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
    }

}
