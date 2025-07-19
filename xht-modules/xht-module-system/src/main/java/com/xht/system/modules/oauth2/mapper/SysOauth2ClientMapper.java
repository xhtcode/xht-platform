package com.xht.system.modules.oauth2.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Mapper
public interface SysOauth2ClientMapper extends BaseMapperX<SysOauth2ClientEntity> {
}
