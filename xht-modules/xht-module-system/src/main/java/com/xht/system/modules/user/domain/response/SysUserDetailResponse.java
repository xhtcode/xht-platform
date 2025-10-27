package com.xht.system.modules.user.domain.response;

import com.xht.framework.core.domain.response.IResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户详细响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户详细响应信息")
public final class SysUserDetailResponse implements IResponse {

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCard;

    /**
     * 用户性别
     */
    @Schema(description = "用户性别")
    private Integer gender;

    /**
     * 出生日期
     */
    @Schema(description = "出生日期")
    private LocalDate birthDate;

    /**
     * 用户年龄
     */
    @Schema(description = "用户年龄")
    private Integer age;

    /**
     * 地址
     */
    @Schema(description = "用户地址")
    private String address;

    /**
     * 邮政编码
     */
    @Schema(description = "邮政编码")
    private String postalCode;

    /**
     * 紧急联系人
     */
    @Schema(description = "紧急联系人")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @Schema(description = "紧急联系人电话")
    private String emergencyPhone;

    /**
     * 用户民族
     */
    @Schema(description = "用户民族")
    private String nation;

}
