package com.xht.auth.authentication.dao.mapper;

import com.xht.auth.authentication.domain.response.SysOauth2ClientScopeResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * oauth2 客户端授权范围Mapper接口
 *
 * @author xht
 **/
@Mapper
public interface SysOauth2ClientScopeMapper {


    /**
     * 根据客户端id查询授权范围
     * @param clientId 客户端id
     * @return 授权范围
     */
    List<SysOauth2ClientScopeResponse> findByClientId(String clientId);
}
