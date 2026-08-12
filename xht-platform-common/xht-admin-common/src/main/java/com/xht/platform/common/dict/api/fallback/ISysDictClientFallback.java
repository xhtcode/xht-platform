package com.xht.platform.common.dict.api.fallback;

import com.xht.framework.common.domain.R;
import com.xht.framework.openfeign.fallback.BasicFallback;
import com.xht.platform.common.dict.api.ISysDictClient;
import com.xht.platform.common.dict.domain.DictVO;

import java.util.List;

/**
 * 描述：字典项查询服务 Fallback
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

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Override
    public R<List<DictVO>> getByDictCode(String dictCode) {
        return error();
    }

}
