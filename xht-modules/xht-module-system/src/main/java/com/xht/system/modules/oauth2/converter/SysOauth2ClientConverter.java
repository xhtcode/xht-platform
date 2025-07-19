package com.xht.system.modules.oauth2.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientFormRequest;
import com.xht.system.modules.oauth2.domian.response.SysOauth2ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOauth2ClientConverter extends BasicConverter<SysOauth2ClientEntity, SysOauth2ClientFormRequest, SysOauth2ClientResponse> {
}
