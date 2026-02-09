package com.xht.auth.security.oatuh2.server.authorization.client;

import com.xht.auth.authentication.dao.IAuthenticationDao;
import com.xht.auth.authentication.dto.Oauth2ClientDTO;
import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.framework.cache.service.RedisService;
import com.xht.framework.cache.utils.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
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
public class Oauth2RegisteredClientRepository implements RegisteredClientRepository {

    private final IAuthenticationDao authenticationDao;

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
        Oauth2ClientDTO clientDTO = null;
        Long expire = redisService.getExpire(key);
        if (expire > 0) {
            clientDTO = redisService.getSet(key);
        }
        clientDTO = Optional.ofNullable(clientDTO).orElseGet(() -> authenticationDao.findClientDetailsById(clientId));
        if (Objects.nonNull(clientDTO)) {
            redisService.set(key, clientDTO, xhtOauth2Properties.getClient().getTimeout(), TimeUnit.SECONDS);
        }
        return Optional.ofNullable(clientDTO)
                .map(new OAuth2RegisteredClientFunction()).orElse(null);
    }

}
