package com.xht.cloud.oauth2.feign;

import com.xht.boot.oauth2.domain.dto.OAuth2RegisteredClientDTO;
import com.xht.cloud.oauth2.feign.factory.RemoteRegisteredClientFallbackFactory;
import com.xht.framework.core.constant.ServiceNameConstant;
import com.xht.framework.core.domain.R;
import com.xht.framework.openfeign.annotation.NoAuthentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 远程客户端详情服务接口
 *
 * @author xht
 **/
// @formatter:off
@FeignClient(
        contextId = "remoteRegisteredClientService",
        value = ServiceNameConstant.SYSTEM_SERVICE,
        fallbackFactory = RemoteRegisteredClientFallbackFactory.class
)
public interface RemoteRegisteredClientService {

    /**
     * 通过clientId 查询客户端信息 (未登录，需要无token 内部调用)
     *
     * @param clientId 用户名
     * @return R
     */
    @NoAuthentication
    @ResponseBody
    @GetMapping("/api/sys/oauth2/client/{clientId}")
    R<OAuth2RegisteredClientDTO> getClientDetailsById(@PathVariable("clientId") String clientId);

}
// @formatter:on