package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 项目响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "项目响应信息")
public class GenTemplateGroupResponse extends BasicResponse {

    /**
     * 模板分组id
     */
    @Schema(description = "模板分组id")
    private Long id;

    /**
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String groupName;

    /**
     * 分组描述
     */
    @Schema(description = "分组描述")
    private String groupDesc;
}
