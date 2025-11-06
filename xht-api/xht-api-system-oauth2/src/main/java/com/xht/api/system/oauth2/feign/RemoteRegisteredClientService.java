package com.xht.api.system.oauth2.feign;

import com.xht.api.system.oauth2.dto.OAuth2RegisteredClientDTO;
import com.xht.api.system.oauth2.feign.factory.RemoteRegisteredClientFallbackFactory;
import com.xht.framework.core.constant.ServiceNameConstant;
import com.xht.framework.core.domain.R;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
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
        value = "xht-system",
        fallbackFactory = RemoteRegisteredClientFallbackFactory.class
)
public interface RemoteRegisteredClientService {

    /**
     * 通过clientId 查询客户端信息 (未登录，需要无token 内部调用)
     *
     * @param clientId 用户名
     * @return R
     */
    @FeignIgnoreAuth
    @ResponseBody
    @GetMapping("/api/sys/oauth2/client/{clientId}")
    R<OAuth2RegisteredClientDTO> getClientDetailsById(@PathVariable("clientId") String clientId);

}
// @formatter:on