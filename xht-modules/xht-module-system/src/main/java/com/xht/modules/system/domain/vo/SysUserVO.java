package com.xht.modules.system.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.modules.system.domain.response.SysPostResponse;
import com.xht.modules.system.domain.response.SysUserDetailResponse;
import com.xht.modules.system.domain.response.SysUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户信息视图对象响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息视图对象响应信息")
public class SysUserVO extends SysUserResponse implements IVO {

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

}
