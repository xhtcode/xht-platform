package com.xht.auth.consent.converter;

import com.xht.auth.consent.domain.response.SysOauth2AuthorizationConsentResponse;
import com.xht.auth.consent.entity.SysOauth2AuthorizationConsentEntity;
import com.xht.framework.mybatis.converter.PageConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 默认授权确认信息转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOauth2AuthorizationConsentConverter extends PageConverter<SysOauth2AuthorizationConsentEntity, SysOauth2AuthorizationConsentResponse> {
}
