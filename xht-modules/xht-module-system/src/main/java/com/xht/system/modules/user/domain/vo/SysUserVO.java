package com.xht.system.modules.user.domain.vo;
import java.time.LocalDate;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.alibaba.nacos.shaded.io.grpc.internal.JsonUtil;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import java.time.LocalDateTime;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.framework.core.jackson.JsonUtils;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.system.modules.user.domain.response.SysUserDetailResponse;
import com.xht.system.modules.user.domain.response.SysUserResponse;
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
