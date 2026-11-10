package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 水印等级
 * <p>等级越高管控越严：off &lt; l1 &lt; l2；用户覆盖只允许调高</p>
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum WatermarkLevelEnum implements XhtEnum<String> {

    /**
     * 无水印
     */
    OFF("off", "无水印", 0),

    /**
     * 前端水印
     */
    L1("l1", "前端水印", 1),

    /**
     * 服务端烧录水印
     */
    L2("l2", "服务端水印", 2),
    ;

    @JsonValue
    private final String value;

    private final String desc;

    /**
     * 等级权重
     */
    private final int rank;

    /**
     * 是否比目标等级更严格
     *
     * @param other 目标等级
     * @return true：更严格
     */
    public boolean stricterThan(WatermarkLevelEnum other) {
        return other != null && this.rank > other.rank;
    }

    /**
     * 取更严格的等级
     *
     * @param a 等级 a
     * @param b 等级 b
     * @return 更严格的等级
     */
    public static WatermarkLevelEnum max(WatermarkLevelEnum a, WatermarkLevelEnum b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.rank >= b.rank ? a : b;
    }

    /**
     * 根据编码解析，未匹配返回 null
     *
     * @param value 编码
     * @return 枚举
     */
    @JsonCreator
    public static WatermarkLevelEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (WatermarkLevelEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
