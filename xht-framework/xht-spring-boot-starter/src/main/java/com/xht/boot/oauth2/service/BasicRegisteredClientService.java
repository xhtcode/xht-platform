package com.xht.boot.oauth2.service;

import com.xht.boot.oauth2.domain.dto.OAuth2RegisteredClientDTO;
import com.xht.boot.oauth2.feign.RemoteRegisteredClientService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.jackson.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import java.util.Objects;

@Slf4j
public class BasicRegisteredClientService implements RegisteredClientRepository {

    private final RedisRegisteredClientRepository redisRegisteredClientRepository;

    private final RemoteRegisteredClientService remoteRegisteredClientService;


    public BasicRegisteredClientService(RedisRegisteredClientRepository redisRegisteredClientRepository, RemoteRegisteredClientService remoteRegisteredClientService) {
        this.redisRegisteredClientRepository = redisRegisteredClientRepository;
        this.remoteRegisteredClientService = remoteRegisteredClientService;
        log.info("BasicRegisteredClientService Registered client repository: initialized");
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        RegisteredClient byClientId = this.redisRegisteredClientRepository.findByClientId(clientId);
        if (Objects.nonNull(byClientId)) {
            return byClientId;
        }
        R<OAuth2RegisteredClientDTO> clientDetailsById = remoteRegisteredClientService.getClientDetailsById(clientId);
        System.out.println(JsonUtils.toJsonString(clientDetailsById));
        return null;
    }


}