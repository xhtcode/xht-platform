package com.xht.system.modules.oauth2.converter;

import com.xht.api.system.oauth2.dto.OAuth2RegisteredClientDTO;
import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.oauth2.domain.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domain.form.SysOauth2ClientForm;
import com.xht.system.modules.oauth2.domain.response.SysOauth2ClientResponse;
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

    /**
     * 实体类转DTO
     *
     * @param sysOauth2ClientEntity 实体类
     * @return OAuth2RegisteredClientDTO
     */
    OAuth2RegisteredClientDTO toDto(SysOauth2ClientEntity sysOauth2ClientEntity);

}
