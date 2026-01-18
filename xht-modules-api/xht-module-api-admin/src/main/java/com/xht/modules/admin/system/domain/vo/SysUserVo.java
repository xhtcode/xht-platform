package com.xht.modules.admin.system.domain.vo;

import com.xht.modules.admin.system.domain.response.SysPostResponse;
import com.xht.modules.admin.system.domain.response.SysUserDetailResponse;
import com.xht.modules.admin.system.domain.response.SysUserResponse;
import com.xht.framework.core.domain.vo.IVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 用户信息视图对象响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息视图对象响应信息")
public class SysUserVo extends SysUserResponse implements IVO {

    /**
     * 注册日期
     */
    @Schema(description = "注册日期")
    private LocalDateTime registerDate;

    /**
     * 用户详细信息 根据不同类型返回不同信息
     */
    @Schema(description = "用户详细信息")
    private SysUserDetailResponse profile;

    /**
     * 用户所在的岗位信息
     */
    @Schema(description = "用户所在的岗位信息")
    private List<SysPostResponse> postInfos;

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
}
