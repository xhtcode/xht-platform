package com.xht.api.system.client.fallback;

import com.xht.api.system.client.dto.OAuth2RegisteredClientDTO;
import com.xht.api.system.client.feign.RemoteRegisteredClientService;
import com.xht.framework.core.domain.R;
import com.xht.framework.openfeign.fallback.BasicFallback;

/**
 * oauth2 客户端 Fallback
 *
 * @author xht
 **/
public class RemoteRegisteredClientFallback extends BasicFallback implements RemoteRegisteredClientService {

    /**
     * 构造函数，创建BasicFallback实例并记录错误日志
     *
     * @param cause 异常原因
     */
    public RemoteRegisteredClientFallback(Throwable cause) {
        super(cause);
    }

    /**
     * 通过clientId 查询客户端信息 (未登录，需要无token 内部调用)
     *
     * @param clientId 用户名
     * @return R
     */
    @Override
    public R<OAuth2RegisteredClientDTO> getClientDetailsById(String clientId) {
        return error();
    }

}
