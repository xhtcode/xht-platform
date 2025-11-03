package com.xht.auth.security.oatuh2.server.authorization.client;

import com.xht.api.system.oauth2.dto.OAuth2RegisteredClientDTO;
import com.xht.api.system.oauth2.feign.RemoteRegisteredClientService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ROptional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

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
        R<OAuth2RegisteredClientDTO> clientDetailsById = remoteRegisteredClientService.getClientDetailsById(clientId);
        if (clientDetailsById == null) {
            return null;
        }
        ROptional<OAuth2RegisteredClientDTO> rOptional = ROptional.of(clientDetailsById);
        return rOptional.get().map(new OAuth2RegisteredClientFunction()).orElse(null);
    }

}
