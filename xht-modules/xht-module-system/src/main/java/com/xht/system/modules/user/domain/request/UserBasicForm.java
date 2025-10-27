package com.xht.system.modules.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户基本信息表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户基本信息表单请求参数")
public class UserBasicForm extends FormRequest {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

}
