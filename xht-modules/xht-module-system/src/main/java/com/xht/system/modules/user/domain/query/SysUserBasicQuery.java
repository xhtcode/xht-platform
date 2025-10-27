package com.xht.system.modules.user.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.common.enums.UserTypeEnums;
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
public class SysUserBasicQuery extends PageBasicQuery {

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnums userType;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private UserStatusEnums userStatus;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String userPhone;

    /**
     * 部门id
     */
    @Schema(description = "部门ID")
    private Long deptId;

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
     * 用户年龄
     */
    @Schema(description = "用户年龄")
    private Integer age;

    /**
     * 出生开始日期
     */
    @Schema(description = "出生开始日期", example = "1990-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDateStart;

    /**
     * 出生结束日期
     */
    @Schema(description = "出生结束日期", example = "2000-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDateEnd;


}
