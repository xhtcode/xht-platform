package com.xht.framework.common.domain.dto;

import com.xht.framework.common.domain.XhtRequest;
import com.xht.framework.common.domain.response.XhtResponse;
import com.xht.framework.common.domain.vo.XhtVO;

import java.io.Serializable;

/**
 * 数据传输对象
 *
 * @author xht
 **/
public interface XhtDTO extends XhtRequest, XhtResponse, XhtVO, Serializable {
}
