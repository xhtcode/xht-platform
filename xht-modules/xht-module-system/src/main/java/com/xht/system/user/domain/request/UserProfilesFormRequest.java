package com.xht.system.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户信息创建请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息创建请求参数")
public class UserProfilesFormRequest extends FormRequest {

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCardNumber;

    /**
     * 性别(1-男,2-女,3-其他)
     */
    @Schema(description = "性别，1：男，2：女，3：其他")
    private Integer gender;

    /**
     * 出生日期
     */
    @Schema(description = "出生日期")
    private LocalDate birthDate;

    /**
     * 年龄(可计算字段)
     */
    @Schema(description = "年龄，可计算字段")
    private Integer age;

    /**
     * 地址
     */
    @Schema(description = "地址")
    private String address;

    /**
     * 邮政编码
     */
    @Schema(description = "邮政编码")
    private String postalCode;
}
