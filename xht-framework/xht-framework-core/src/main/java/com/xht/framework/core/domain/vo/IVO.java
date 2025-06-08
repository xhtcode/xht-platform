package com.xht.framework.core.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 接口返回值对象接口
 * 所有接口返回值对象都必须实现此接口
 *
 * @author xht
 **/
@Schema(description = "接口返回值对象")
public interface IVO extends Serializable {
}
