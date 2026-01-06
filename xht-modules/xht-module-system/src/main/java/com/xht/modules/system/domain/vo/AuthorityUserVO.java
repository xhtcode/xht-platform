package com.xht.modules.system.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户权限响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户权限响应信息")
public class AuthorityUserVO implements IVO {

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private SysUserVO userInfo;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roleCodes;

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<String> menuButtonCodes;

    /**
     * 数据范围(1-全部数据权限,2-自定数据权限,3-本部门数据权限,4-本部门及以下数据权限,5-仅本人数据权限)
     */
    @Schema(description = "数据范围")
    private Integer dataScope;
}
