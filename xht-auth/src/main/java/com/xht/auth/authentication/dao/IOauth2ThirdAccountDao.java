package com.xht.auth.authentication.dao;

import com.xht.auth.authentication.entity.Oauth2ThirdAccountEntity;
import com.xht.framework.mybatis.repository.MapperRepository;

/**
 * 描述： 第三方账号DAO接口
 *
 * @author xht
 **/
public interface IOauth2ThirdAccountDao extends MapperRepository<Oauth2ThirdAccountEntity> {

    /**
     * 根据openid查询
     *
     * @param openid 第三方账号的openid
     * @return 第三方账号信息
     */
    Oauth2ThirdAccountEntity findByOpenid(String openid);

    /**
     * 注册用户
     *
     * @param dbAccountEntity 第三方账号信息
     */
    void registerUser(Oauth2ThirdAccountEntity dbAccountEntity);

}
