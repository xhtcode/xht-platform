package com.xht.framework.validation.bo;

import com.xht.framework.common.domain.bo.XhtBO;

/**
 * 描述 ：异常对象bo
 *
 * @author : xht
 **/
public record ValidationExceptionBO(String filedName, String message) implements XhtBO {

}
