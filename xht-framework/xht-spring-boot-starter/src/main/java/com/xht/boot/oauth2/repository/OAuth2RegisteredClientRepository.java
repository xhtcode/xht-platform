package com.xht.boot.oauth2.repository;

import com.xht.boot.oauth2.entity.OAuth2RegisteredClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2RegisteredClientRepository extends CrudRepository<OAuth2RegisteredClient, String> {

    /**
     * 根据clientId查询
     *
     * @param clientId 客户端id
     * @return 客户端信息
     */
    OAuth2RegisteredClient findByClientId(String clientId);

}