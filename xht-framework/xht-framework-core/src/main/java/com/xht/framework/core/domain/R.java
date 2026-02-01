package com.xht.framework.core.domain;

import com.xht.framework.core.constant.basic.RConstants;
import com.xht.framework.core.enums.DataTypeEnums;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.exception.code.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 响应结果封装
 * 用于封装API请求的响应结果，包括返回码、返回信息、是否成功以及返回的数据。
 *
 * @author xht
 **/
@Getter
@Schema(description = "响应结果")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class R<T> {

    @Schema(description = "是否成功")
    private final Boolean ok;

    @Schema(description = "返回码")
    private final Integer code;

    @Schema(description = "返回信息")
    private final String msg;

    @Schema(description = "返回数据")
    private final T data;

    @Schema(description = "数据类型")
    private final DataTypeEnums dataType;

    /**
     * 创建成功的响应构建器
     *
     * @return RBuilder 响应构建器实例
     */
    public static RBuilder ok() {
        return new RBuilder(Boolean.TRUE).code(RConstants.SUCCESS).msg(RConstants.SUCCESS_MSG);
    }

    /**
     * 创建错误的响应构建器，默认错误码和成功消息（可能存在逻辑问题）
     *
     * @return RBuilder 响应构建器实例
     */
    public static RBuilder error() {
        return error(RConstants.FAIL).code(RConstants.FAIL).msg(RConstants.FAIL_MSG);
    }

    /**
     * 根据指定错误码创建错误的响应构建器
     *
     * @param code 错误码
     * @return RBuilder 响应构建器实例
     * @throws UtilException 当错误码等于SUCCESS时抛出异常
     */
    public static RBuilder error(Integer code) {
        if (Objects.equals(RConstants.SUCCESS, code)) {
            throw new UtilException("code cannot be SUCCESS");
        }
        return new RBuilder(Boolean.TRUE).code(Objects.requireNonNullElse(code, RConstants.FAIL)).msg(RConstants.FAIL_MSG);
    }

    public static class RBuilder {
        /**
         * 是否成功
         */
        private final Boolean ok;

        /**
         * 返回码
         */
        private Integer code;

        /**
         * 返回信息
         */
        private String msg;

        /**
         * 数据类型
         */
        private DataTypeEnums dataType;

        /**
         * 构造响应构建器实例
         *
         * @param ok 是否成功标识
         */
        RBuilder(Boolean ok) {
            this.ok = ok;
            this.dataType = DataTypeEnums.NORMAL;
        }

        /**
         * 设置返回码
         *
         * @param code 返回码
         */
        public RBuilder code(Integer code) {
            if (Objects.equals(this.code, RConstants.SUCCESS)) {
                throw new UtilException("code cannot be SUCCESS");
            }
            this.code = code;
            return this;
        }

        /**
         * 设置响应消息
         *
         * @param msg 响应消息
         * @return RBuilder 当前构建器实例
         */
        public RBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        /**
         * 设置数据类型为加密类型
         *
         * @return RBuilder 当前构建器实例
         */
        public RBuilder encrypt() {
            this.dataType = DataTypeEnums.ENCRYPT;
            return this;
        }

        /**
         * 设置错误码和错误信息
         *
         * @param errorCode 错误码对象，包含错误码和错误信息
         * @return 返回当前RBuilder实例，支持链式调用
         */
        public RBuilder info(ErrorCode errorCode) {
            this.code = errorCode.getCode();
            this.msg = errorCode.getMsg();
            return this;
        }

        /**
         * 构建响应对象，不包含数据
         *
         * @param <T> 数据泛型类型
         * @return R<T> 响应对象实例
         */
        public <T> R<T> build() {
            return build(null);
        }

        /**
         * 构建响应对象，包含指定数据
         *
         * @param <T>  数据泛型类型
         * @param data 响应数据
         * @return R<T> 响应对象实例
         */
        public <T> R<T> build(T data) {
            return new R<>(ok, code, msg, data, dataType);
        }

    }

}
