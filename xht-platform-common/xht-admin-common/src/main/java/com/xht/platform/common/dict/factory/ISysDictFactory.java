package com.xht.platform.common.dict.factory;

import com.xht.platform.common.dict.domain.DictVO;

import java.util.List;

/**
 * 描述：字典工厂
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
    List<DictVO> getDictList(String dictCode);

}
