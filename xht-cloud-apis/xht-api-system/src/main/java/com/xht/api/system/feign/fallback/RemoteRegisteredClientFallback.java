package com.xht.api.system.feign.fallback;

import com.xht.api.system.domain.response.SysOauth2ClientResponse;
import com.xht.api.system.feign.RemoteRegisteredClientService;
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
    public R<SysOauth2ClientResponse> getClientDetailsById(String clientId) {
        return error();
    }

}
