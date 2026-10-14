package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FileStatusEnum implements XhtEnum<Integer> {
    /**
     * 已上传
     */
    UPLOADED(1, "已上传"),
    /**
     * 已关联业务
     */
    ASSOCIATED(2, "已关联业务"),
    /**
     * 已删除
     */
    DELETED(99, "已删除"),
    ;

    /**
     * 状态值
     */
    @JsonValue
    private final Integer value;

    /**
     * 状态描述
     */
    private final String desc;
}
