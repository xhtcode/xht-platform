package com.xht.cloud.oauth2.service;

import com.xht.boot.oauth2.domain.dto.OAuth2RegisteredClientDTO;
import com.xht.boot.oauth2.function.OAuth2RegisteredClientFunction;
import com.xht.boot.oauth2.service.RedisRegisteredClientService;
import com.xht.cloud.oauth2.feign.RemoteRegisteredClientService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ROptional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import java.util.Objects;

@Slf4j
public class BasicRegisteredClientRepository implements RegisteredClientRepository {

    @Resource
    private RedisRegisteredClientService redisRegisteredClientService;

    @Resource
    private RemoteRegisteredClientService remoteRegisteredClientService;


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
        RegisteredClient byClientId = this.redisRegisteredClientService.findByClientId(clientId);
        if (Objects.nonNull(byClientId)) {
            return byClientId;
        }
        R<OAuth2RegisteredClientDTO> clientDetailsById = remoteRegisteredClientService.getClientDetailsById(clientId);
        ROptional<OAuth2RegisteredClientDTO> rOptional = ROptional.of(clientDetailsById);
        RegisteredClient registeredClient = rOptional.get().map(new OAuth2RegisteredClientFunction()).orElse(null);
        redisRegisteredClientService.save(registeredClient);
        return registeredClient;
    }

}