package com.xht.framework.core.domain;

import com.xht.framework.core.enums.DataTypeEnum;
import com.xht.framework.core.exception.code.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import static com.xht.framework.core.constant.basic.Constants.SUCCESS;
import static com.xht.framework.core.constant.basic.Constants.SUCCESS_MSG;
import static com.xht.framework.core.exception.code.GlobalErrorStatusCode.ERROR;

/**
 * 响应结果封装
 * 用于封装API请求的响应结果，包括返回码、返回信息、是否成功以及返回的数据。
 *
 * @author xht
 **/
@Data
@Schema(description = "响应结果")
@SuppressWarnings("unused")
public class R<T> {

    @Schema(description = "返回码")
    private Integer code;

    @Schema(description = "返回信息")
    private String msg;

    @Schema(description = "是否成功")
    private Boolean ok;

    @Schema(description = "返回数据")
    private T data;

    @Schema(description = "数据类型")
    private DataTypeEnum dataType;

    /**
     * 构造函数，用于初始化响应结果对象
     *
     * @param code 返回码
     * @param ok   是否成功
     * @param msg  返回信息
     * @param data 返回数据
     */
    public R(Integer code, boolean ok, String msg, T data) {
        this.code = code;
        this.ok = ok;
        this.msg = msg;
        this.data = data;
        this.dataType = DataTypeEnum.NORMAL;
    }

    /**
     * 构造函数，用于初始化响应结果对象，不包含返回数据
     *
     * @param code 返回码
     * @param ok   是否成功
     * @param msg  返回信息
     */
    public R(Integer code, boolean ok, String msg) {
        this.code = code;
        this.ok = ok;
        this.msg = msg;
        this.dataType = DataTypeEnum.NORMAL;
    }

    /**
     * 构造函数，使用自定义的错误码对象来初始化响应结果对象
     *
     * @param errorCode 错误码对象
     * @param ok        是否成功
     * @param msg       返回信息
     * @param data      返回数据
     */
    public R(ErrorCode errorCode, boolean ok, String msg, T data) {
        this.code = errorCode.getCode();
        this.ok = ok;
        if (StringUtils.isNotBlank(msg)) {
            this.msg = msg;
        } else {
            this.msg = errorCode.getMsg();
        }
        this.data = data;
        this.dataType = DataTypeEnum.NORMAL;
    }

    /**
     * 静态方法，返回默认的成功响应结果
     *
     * @param <T> 返回数据的类型
     * @return 成功响应结果
     */
    public static <T> R<T> ok() {
        return new R<>(SUCCESS, true, SUCCESS_MSG, null);
    }

    /**
     * 静态方法，返回带有指定数据的成功响应结果
     *
     * @param data 返回数据
     * @param <T>  返回数据的类型
     * @return 成功响应结果
     */
    public static <T> R<T> ok(T data) {
        return new R<>(SUCCESS, true, SUCCESS_MSG, data);
    }

    /**
     * 静态方法，返回带有自定义成功信息的成功响应结果
     *
     * @param msg 自定义成功信息
     * @param <T> 返回数据的类型
     * @return 成功响应结果
     */
    public static <T> R<T> okMsg(String msg) {
        return new R<>(SUCCESS, true, msg, null);
    }

    // -------------------------------------------- 错误码 --------------------------------------------


    /**
     * 静态方法，根据错误码对象返回错误的响应结果
     *
     * @param errorCode 错误码对象
     * @param <T>       返回数据的类型
     * @return 错误的响应结果
     */
    public static <T> R<T> error(ErrorCode errorCode) {
        return new R<>(errorCode, false, null, null);
    }


    /**
     * 静态方法，根据错误码对象返回错误的响应结果
     *
     * @param <T> 返回数据的类型
     * @return 错误的响应结果
     */
    public static <T> R<T> errorMsg(String msg) {
        return error(ERROR, msg);
    }


    /**
     * 静态方法，根据错误码对象返回错误的响应结果，并允许自定义错误信息
     *
     * @param msg  自定义错误信息
     * @param data 错误数据
     * @return 错误的响应结果
     */
    public static <T> R<T> errorMsgData(String msg, T data) {
        return new R<>(ERROR, false, msg, data);
    }

    /**
     * 静态方法，根据错误码对象返回错误的响应结果，并允许自定义错误信息
     *
     * @param errorCode 错误码对象
     * @param msg       自定义错误信息
     * @param <T>       返回数据的类型
     * @return 错误的响应结果
     */
    public static <T> R<T> error(ErrorCode errorCode, String msg) {
        return new R<>(errorCode, false, msg, null);
    }

    /**
     * 静态方法，根据错误码对象返回错误的响应结果，并允许自定义错误信息
     *
     * @param errorCode 错误码对象
     * @param msg       自定义错误信息
     * @param <T>       返回数据的类型
     * @return 错误的响应结果
     */
    public static <T> R<T> error(int errorCode, String msg) {
        return new R<>(errorCode, false, msg, null);
    }

    /**
     * 静态方法，根据错误码对象返回错误的响应结果，并携带错误数据
     *
     * @param data 错误数据
     * @param <T>  返回数据的类型
     * @return 错误的响应结果
     */
    public static <T> R<T> errorData(T data) {
        return new R<>(ERROR, false, null, data);
    }

    /**
     * 静态方法，根据错误码对象返回错误的响应结果，并携带错误数据
     *
     * @param errorCode 错误码对象
     * @param data      错误数据
     * @param <T>       返回数据的类型
     * @return 错误的响应结果
     */
    public static <T> R<T> errorData(ErrorCode errorCode, T data) {
        return new R<>(errorCode, false, null, data);
    }
}
