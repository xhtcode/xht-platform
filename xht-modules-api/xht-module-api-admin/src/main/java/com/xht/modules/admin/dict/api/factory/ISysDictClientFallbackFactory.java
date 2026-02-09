package com.xht.modules.admin.dict.api.factory;

import com.xht.modules.admin.dict.api.ISysDictClient;
import com.xht.modules.admin.dict.api.fallback.ISysDictClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 字典项查询服务
 *
 * @author xht
 **/
@Slf4j
@Component
public class ISysDictClientFallbackFactory implements FallbackFactory<ISysDictClient> {

    @Override
    public ISysDictClient create(Throwable cause) {
        return new ISysDictClientFallback(cause);
    }

}
