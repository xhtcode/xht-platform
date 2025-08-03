package com.xht.system.modules.user.domain.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xht.framework.core.domain.request.PageQueryRequest;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 用户查询分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用于分页查询用户信息的分页查询参数")
public class UserQueryRequest extends PageQueryRequest {


    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userAccount;


    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @Schema(description = "账号状态(1-正常,2-锁定,3-禁用,4-过期)")
    private UserStatusEnums userStatus;

    /**
     * 用户昵称
     */
    @Schema(description = "用户的昵称，用于显示", example = "星月")
    private String userName;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号", example = "11010519491231002X")
    private String idCardNumber;

    /**
     * 性别(1-男,2-女,3-其他)
     */
    @Schema(description = "性别，1-男，2-女，3-其他", example = "1")
    private Integer gender;

    /**
     * 出生日期
     */
    @Schema(description = "出生开始日期", example = "1990-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDateStart;
    @Schema(description = "出生结束日期", example = "2000-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDateEnd;

    /**
     * 年龄(可计算字段)
     */
    @Schema(description = "年龄（可计算字段）", example = "30")
    private Integer age;

    /**
     * 邮政编码
     */
    @Schema(description = "邮政编码", example = "100020")
    private String postalCode;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID", example = "1")
    private Long deptId;

}
