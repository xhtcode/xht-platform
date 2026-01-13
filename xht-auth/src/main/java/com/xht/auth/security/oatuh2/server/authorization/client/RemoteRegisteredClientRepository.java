package com.xht.auth.security.oatuh2.server.authorization.client;

import com.xht.api.system.domain.response.SysOauth2ClientResponse;
import com.xht.api.system.feign.RemoteRegisteredClientService;
import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.framework.cache.service.RedisService;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ROptional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.xht.framework.oauth2.constant.Oauth2Constant.OAUTH2_CLIENT_KEY_PREFIX;

/**
 * 自定义注册客户端仓库
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteRegisteredClientRepository implements RegisteredClientRepository {

    private final RemoteRegisteredClientService remoteRegisteredClientService;

    private final RedisService redisService;

    private final XhtOauth2Properties xhtOauth2Properties;

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException("save operation is not supported");
    }

    @Override
    public RegisteredClient findById(String id) {
        return findByClientId(id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        String key = Keys.createKey(OAUTH2_CLIENT_KEY_PREFIX, clientId);
        log.debug("根据客户端id:`{}`查询客户端 缓存key {}", clientId, key);
        RegisteredClient catchRegisteredClient = redisService.get(key);
        if (Objects.nonNull(catchRegisteredClient)) {
            return catchRegisteredClient;
        }
        R<SysOauth2ClientResponse> clientDetailsById = remoteRegisteredClientService.getClientDetailsById(clientId);
        if (clientDetailsById == null) {
            return null;
        }
        ROptional<SysOauth2ClientResponse> rOptional = ROptional.of(clientDetailsById);
        RegisteredClient client = rOptional.get().map(new OAuth2RegisteredClientFunction()).orElse(null);
        redisService.set(key, client, xhtOauth2Properties.getClient().getTimeout(), TimeUnit.SECONDS);
        return client;
    }

}
