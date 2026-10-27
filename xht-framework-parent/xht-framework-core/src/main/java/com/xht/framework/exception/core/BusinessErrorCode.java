package com.xht.framework.exception.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务错误状态码
 * 编码规则：5 + 模块(2位) + 序号(2位)
 * 5：固定前缀，代表业务异常（非HTTP状态码）
 * 00：全局通用模块，所有业务共用
 * 01：数据字典模块
 * 02：附件/文件模块
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements ErrorCode {

    // ====================== 全局通用模块 500xx ======================
    /**
     * 系统错误
     */
    SYSTEM_ERROR(50001, "服务器内部异常，请稍后重试"),
    /**
     * 参数错误
     */
    PARAM_ERROR(50002, "请求参数校验不通过，请检查入参"),
    /**
     * 数据已存在
     */
    DATA_EXIST(50003, "数据已存在，请勿重复提交"),
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(50004, "目标数据不存在"),
    /**
     * 数据类型错误
     */
    DATA_TYPE_ERROR(50005, "数据类型不符合要求"),
    /**
     * 系统内置禁止操作
     */
    SYSTEM_FORBIDDEN(50006, "系统内置数据，不允许执行该操作"),
    /**
     * 非法操作
     */
    ILLEGAL_OPERATION(50007, "非法操作，请求不合法"),
    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(50008, "请求处理超时，请重试"),
    /**
     * 操作频繁
     */
    FREQUENT_OPERATION(50009, "操作过于频繁，请稍后再试"),
    /**
     * 版本冲突（乐观锁）
     */
    VERSION_CONFLICT(50010, "数据已被其他用户修改，请刷新后重试"),
    /**
     * 数据转换失败
     */
    DATA_CONVERSION_ERROR(50011, "数据转换失败，请检查数据格式"),

    // ====================== 数据字典模块 501xx ======================
    /**
     * 字典不存在
     */
    DICT_CODE_EXIST(50101, "字典编码已存在"),
    /**
     * 字典不存在
     */
    DICT_NOT_EXIST(50102, "字典信息不存在"),
    /**
     * 字典状态异常，禁止操作
     */
    DICT_STATUS_ERROR(50103, "字典状态异常，禁止操作"),
    /**
     * 字典下存在字典项，禁止删除
     */
    DICT_EXIST_DICT_ITEM(50104, "字典下存在字典项，禁止删除"),
    /**
     * 字典编码重复
     */
    DICT_ITEM_CODE_EXIST(50105, "字典项编码已存在"),
    /**
     * 字典项不存在
     */
    DICT_ITEM_NOT_EXIST(50106, "字典项不存在"),

    // ====================== 附件/文件模块 502xx ======================
    /**
     * 文件内容为空
     */
    FILE_CONTENT_EMPTY(50201, "文件内容为空"),
    /**
     * 附件大小超出限制
     */
    FILE_SIZE_EXCEED(50202, "附件大小超出允许范围"),
    /**
     * 不支持该附件类型
     */
    FILE_TYPE_NOT_SUPPORT(50203, "当前附件类型不支持上传"),
    /**
     * 非法的文件存储路径
     */
    FILE_PATH_ILLEGAL(50204, "非法的文件存储路径"),
    /**
     * 文件已存在
     */
    FILE_EXIST(50205, "同名文件已存在"),
    /**
     * 文件不存在
     */
    FILE_NOT_EXIST(50206, "目标文件不存在"),
    /**
     * 上传附件不能为空
     */
    FILE_UPLOAD_EMPTY(50207, "上传附件不能为空"),
    /**
     * 附件上传失败
     */
    FILE_UPLOAD_FAIL(50208, "附件上传失败，请稍后重试"),
    /**
     * 附件下载失败
     */
    FILE_DOWNLOAD_FAIL(50209, "附件下载失败，请稍后重试"),
    /**
     * 无权访问该文件
     */
    FILE_ACCESS_DENIED(50210, "无权访问该文件"),
    /**
     * 文件已关联业务，无法删除
     */
    FILE_ASSOCIATED(50211, "文件已关联业务，无法删除"),
    /**
     * 文件关联业务失败
     */
    FILE_ASSOCIATE_FAIL(50212, "文件关联业务失败"),
    /**
     * 文件解关联业务失败
     */
    FILE_DISASSOCIATE_FAIL(50213, "文件解关联业务失败"),
    /**
     * 未指定文件上传用户
     */
    FILE_UPLOAD_USER_ILLEGAL(50214, "未指定文件上传用户"),
    /**
     * 当前附件类型不支持预览
     */
    FILE_PREVIEW_NOT_SUPPORT(50215, "当前附件类型不支持预览，请下载后查看"),
    /**
     * 消息推送失败
     */
    MESSAGE_PUSH_FAIL(50301, "消息推送失败，请稍后重试"),

    ;
    /**
     * 业务错误码
     * <p>
     * 编码规则：5 + 模块(2位) + 序号(2位)
     * <p>
     * 注意：这是业务编码，不是HTTP响应状态码
     */
    private final int code;

    /**
     * 错误描述信息
     */
    private final String message;

}

