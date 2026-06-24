package com.xht.auth.authentication.dao.mapper;

import com.xht.auth.authentication.entity.Oauth2ThirdAccountEntity;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 描述： 第三方账号DAO接口
 *
 * @author xht
 */
@Mapper
public interface Oauth2ThirdAccountMapper extends BaseMapperX<Oauth2ThirdAccountEntity> {

    /**
     * 描述： 插入用户信息
     *
     * @param dbAccountEntity 用户信息
     */
    void insertUser(@Param("entity") Oauth2ThirdAccountEntity dbAccountEntity);

    /**
     * 描述： 插入用户信息
     *
     * @param dbAccountEntity 用户信息
     */
    void insertUserInfo(@Param("entity") Oauth2ThirdAccountEntity dbAccountEntity);
}




