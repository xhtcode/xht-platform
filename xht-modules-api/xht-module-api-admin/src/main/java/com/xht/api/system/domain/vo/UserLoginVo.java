package com.xht.api.system.domain.vo;

import com.xht.api.system.domain.response.SysUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * 用户登录信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户登录信息")
public class UserLoginVo extends SysUserResponse {

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String passWord;

    /**
     * 密码盐值
     */
    @Schema(description = "密码盐值")
    private String passWordSalt;

    /**
     * 注册日期
     */
    @Schema(description = "注册日期")
    private LocalDateTime registerDate;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private Set<String> roleCodes = Collections.emptySet();

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private Set<String> menuButtonCodes = Collections.emptySet();

    /**
     * 数据范围(1-全部数据权限,2-自定数据权限,3-本部门数据权限,4-本部门及以下数据权限,5-仅本人数据权限)
     */
    @Schema(description = "数据范围")
    private Integer dataScope;
}
