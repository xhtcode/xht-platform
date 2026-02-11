package com.xht.framework.core.support.dict;

import com.xht.framework.core.support.dict.domain.DictVo;

import java.util.List;

/**
 * 字典工厂
 *
 * @author xht
 **/
public interface ISysDictFactory {

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    List<DictVo> getDictList(String dictCode);

}
