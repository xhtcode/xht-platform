package com.xht.system.modules.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 用户表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户表单请求参数")
public class SysUserForm extends FormRequest {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String userPhone;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String userAvatar;

    /**
     * 部门id
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 用户详情
     */
    @Schema(description = "用户详情")
    private SysUserDetailForm detail;

}