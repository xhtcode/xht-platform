package com.xht.modules.admin.oauth2.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.oauth2.entity.SysOauth2ClientEntity;
import com.xht.modules.admin.oauth2.domain.form.SysOauth2ClientForm;
import com.xht.modules.admin.oauth2.domain.response.SysOauth2ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOauth2ClientConverter extends BasicConverter<SysOauth2ClientEntity, SysOauth2ClientForm, SysOauth2ClientResponse> {

}
