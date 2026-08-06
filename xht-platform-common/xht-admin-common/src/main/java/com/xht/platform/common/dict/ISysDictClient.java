package com.xht.platform.common.dict;

import com.xht.framework.common.domain.R;
import com.xht.platform.common.dict.domain.DictVO;
import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

/**
 * 字典项查询服务
 * @author xht
 **/
public interface ISysDictClient {

    R<List<DictVO>> getByDictCode(@PathVariable String dictCode);
}