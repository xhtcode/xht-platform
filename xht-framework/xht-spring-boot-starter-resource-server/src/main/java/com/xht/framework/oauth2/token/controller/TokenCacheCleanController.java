package com.xht.framework.oauth2.token.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import com.xht.framework.oauth2.token.form.TokenForm;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.security.constant.SecurityConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存清除端点（仅授权服务器可调用）
 */
@Slf4j
@RestController
@ConditionalOnProperty(value = "xht.security.ignore.whites.token-revocation-endpoint", havingValue = "true", matchIfMissing = true)
public class TokenCacheCleanController {

    @Resource
    private TokenInfoLightningCache tokenInfoLightningCache;

    /**
     * 清除单个访问令牌的缓存信息
     *
     * <p>该接口用于撤销单个访问令牌，通过删除令牌缓存使该令牌立即失效。</p>
     *
     * @param tokenForm 包含访问令牌信息的表单对象，必须包含有效的 accessToken
     * @return R<Void> 操作结果封装，成功时返回空响应
     */
    @IgnoreAuth
    @PostMapping(SecurityConstant.RESOURCE_SERVER_TOKEN_CANCEL_URL)
    public R<Void> clearTokenCache(@RequestBody TokenForm tokenForm) {
        tokenInfoLightningCache.deleteTokenInfo(tokenForm.getAccessToken());
        return R.ok().build();
    }

    /**
     * 批量清除多个访问令牌的缓存信息
     *
     * <p>该接口用于批量撤销多个访问令牌，通过一次性删除多个令牌缓存使这些令牌立即失效。</p>
     *
     * @param tokenForm 包含访问令牌列表的表单对象，必须包含有效的 accessTokenList
     * @return R<Void> 操作结果封装，成功时返回空响应
     */
    @IgnoreAuth
    @PostMapping(SecurityConstant.RESOURCE_SERVER_TOKEN_BATCH_CANCEL_URL)
    public R<Void> batchClearTokenCache(@RequestBody TokenForm tokenForm) {
        tokenInfoLightningCache.deleteTokenInfo(tokenForm.getAccessTokenList());
        return R.ok().build();
    }


}