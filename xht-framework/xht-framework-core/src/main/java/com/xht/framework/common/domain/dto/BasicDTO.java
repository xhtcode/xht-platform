package com.xht.framework.common.domain.dto;

import com.xht.framework.common.domain.IRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 数据传输对象
 *
 * @author xht
 **/
@Schema(description = "数据传输对象")
public abstract class BasicDTO implements IRequest, Serializable {
}
