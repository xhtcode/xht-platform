package com.xht.modules.admin.oauth2.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.framework.oauth2.enums.Oauth2ClientAutoApproveEnums;
import com.xht.modules.admin.oauth2.domain.form.SysOauth2ClientForm;
import com.xht.modules.admin.oauth2.domain.response.SysOauth2ClientResponse;
import com.xht.modules.admin.oauth2.entity.SysOauth2ClientEntity;
import org.mapstruct.*;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOauth2ClientConverter extends BasicConverter<SysOauth2ClientEntity, SysOauth2ClientForm, SysOauth2ClientResponse> {

    /**
     * 将创建请求对象转换为实体对象
     *
     * @param form 创建请求对象，非null
     * @return 转换后的实体对象，非null
     */
    @Override
    @Mapping(source = "autoApprove", target = "autoApprove", qualifiedByName = "convertAutoApprove")
    SysOauth2ClientEntity toEntity(SysOauth2ClientForm form);

    /**
     * 将实体对象转换为响应对象
     *
     * @param entity 实体对象，包含从数据库获取的数据，非null
     * @return 转换后的响应对象，用于返回给客户端，非null
     */
    @Override
    @Mapping(source = "autoApprove.value", target = "autoApprove")
    SysOauth2ClientResponse toResponse(SysOauth2ClientEntity entity);


    /**
     * 转换自动授权枚举值
     */
    @Named("convertAutoApprove")
    default Oauth2ClientAutoApproveEnums convertAutoApprove(Integer code) {
        return Oauth2ClientAutoApproveEnums.of(code);
    }

}
