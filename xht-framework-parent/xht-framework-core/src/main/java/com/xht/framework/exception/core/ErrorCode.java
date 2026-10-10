package com.xht.framework.exception.core;

/**
 * 错误码接口
 * <p>
 * 用于统一规范业务错误码与错误描述，实现类通常为错误码枚举，
 * 与 {@link com.xht.framework.exception.BusinessException} 配合使用；
 * 错误码一共分为三种：1）系统错误、2）用户级别错误、3）未预期到的错误
 *
 * @author xht
 **/
public interface ErrorCode {

    /**
     * 获取业务错误码
     * 注意：这是业务编码，不是HTTP响应状态码
     *
     * @return 业务错误码
     */
    int getCode();

    /**
     * 获取错误描述信息
     *
     * @return 错误描述信息
     */
    String getMessage();

}