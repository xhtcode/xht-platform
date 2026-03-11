package com.xht.framework.oauth2.token.form;

import com.xht.framework.core.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 令牌表单
 *
 * @author xht
 **/
@Data
@Schema(description = "令牌表单")
public class TokenForm extends BasicForm {

    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    @NotEmpty(message = "访问令牌不能为空")
    private String accessToken;

    /**
     * 访问令牌列表
     */
    @Schema(description = "访问令牌列表")
    @NotNull(message = "访问令牌列表不能为空")
    private List<String> accessTokenList;

}
