package com.xht.system.modules.oauth2.converter;

import com.xht.boot.oauth2.domain.dto.OAuth2RegisteredClientDTO;
import com.xht.framework.core.jackson.JsonUtils;
import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientFormRequest;
import com.xht.system.modules.oauth2.domian.response.SysOauth2ClientResponse;
import org.mapstruct.*;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOauth2ClientConverter extends BasicConverter<SysOauth2ClientEntity, SysOauth2ClientFormRequest, SysOauth2ClientResponse> {

    /**
     * 实体类转DTO
     *
     * @param sysOauth2ClientEntity 实体类
     * @return OAuth2RegisteredClientDTO
     */
    OAuth2RegisteredClientDTO toDto(SysOauth2ClientEntity sysOauth2ClientEntity);

}
