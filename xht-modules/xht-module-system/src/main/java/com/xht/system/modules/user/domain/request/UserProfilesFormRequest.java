package com.xht.system.modules.user.domain.request;

import com.xht.framework.core.constant.RegexConstant;
import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户信息表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息表单请求参数")
public class UserProfilesFormRequest extends FormRequest {

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 50, message = "真实姓名长度不能超过50个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Pattern(regexp = RegexConstant.ID_CARD, message = "身份证号格式不正确", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "身份证号")
    private String idCardNumber;

    /**
     * 性别(1-男,2-女,3-其他)
     */
    @NotNull(message = "性别参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "性别，1：男，2：女，3：其他")
    private Integer gender;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "出生日期")
    private LocalDate birthDate;

    /**
     * 年龄可以由出生日期计算得到，通常不需要单独校验，除非有特殊需求
     * 年龄(可计算字段)
     */
    @Schema(description = "年龄，可计算字段")
    private Integer age;

    /**
     * 地址
     */
    @NotBlank(message = "地址参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 255, message = "地址长度不能超过255个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "地址")
    private String address;

    /**
     * 邮政编码
     */
    @NotBlank(message = "邮政编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Pattern(regexp = RegexConstant.POSTAL_CODE, message = "邮政编码必须参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "邮政编码")
    private String postalCode;
}
