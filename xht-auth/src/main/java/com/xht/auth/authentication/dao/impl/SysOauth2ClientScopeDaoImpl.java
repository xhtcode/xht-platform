package com.xht.auth.authentication.dao.impl;

import com.xht.auth.authentication.dao.ISysOauth2ClientScopeDao;
import com.xht.auth.authentication.dao.mapper.SysOauth2ClientScopeMapper;
import com.xht.auth.authentication.domain.response.SysOauth2ClientScopeResponse;
import com.xht.framework.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class SysOauth2ClientScopeDaoImpl implements ISysOauth2ClientScopeDao {

    private final SysOauth2ClientScopeMapper sysOauth2ClientScopeMapper;


    /**
     * 根据客户端id查询授权范围
     *
     * @param clientId 客户端id
     * @return 授权范围
     */
    @Override
    public List<SysOauth2ClientScopeResponse> findByClientId(String clientId) {
        log.debug("根据客户端id查询授权范围:`{}`", clientId);
        if (StringUtils.isEmpty(clientId)) {
            return Collections.emptyList();
        }
        return sysOauth2ClientScopeMapper.findByClientId(clientId);
    }
}
