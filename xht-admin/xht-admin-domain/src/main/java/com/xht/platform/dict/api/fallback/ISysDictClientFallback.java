package com.xht.platform.dict.api.fallback;

import com.xht.framework.common.domain.R;
import com.xht.framework.core.dict.domain.DictVO;
import com.xht.framework.openfeign.fallback.BasicFallback;
import  com.xht.platform.dict.api.ISysDictClient;

import java.util.List;

/**
 * 字典项查询服务 Fallback
 *
 * @author xht
 **/
public class ISysDictClientFallback extends BasicFallback implements ISysDictClient {

    /**
     * 构造函数，创建BasicFallback实例并记录错误日志
     *
     * @param cause 异常原因
     */
    public ISysDictClientFallback(Throwable cause) {
        super(cause);
    }

    @Override
    public R<List<DictVO>> getByDictCode(String dictCode) {
        return error();
    }

}
