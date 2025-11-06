package com.xht.system.feign.oauth2;

import com.xht.api.system.oauth2.dto.OAuth2RegisteredClientDTO;
import com.xht.api.system.oauth2.feign.RemoteRegisteredClientService;
import com.xht.framework.core.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.system.modules.oauth2.service.ISysOauth2ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 远程客户端详情服务接口
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class RemoteRegisteredClientServiceImpl implements RemoteRegisteredClientService {

    private final ISysOauth2ClientService sysOauth2ClientService;


    /**
     * 通过clientId 查询客户端信息 (未登录，需要无token 内部调用)
     *
     * @param clientId 用户名
     * @return R
     */
    @Override
    @GetMapping("/api/sys/oauth2/client/{clientId}")
    @IgnoreAuth
    public R<OAuth2RegisteredClientDTO> getClientDetailsById(@PathVariable("clientId") String clientId) {
        return R.ok(sysOauth2ClientService.getClient(clientId));
    }
}
