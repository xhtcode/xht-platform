package com.xht.modules.system.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.system.entity.SysOauth2ClientEntity;
import com.xht.api.system.domain.form.SysOauth2ClientForm;
import com.xht.api.system.domain.response.SysOauth2ClientResponse;
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
