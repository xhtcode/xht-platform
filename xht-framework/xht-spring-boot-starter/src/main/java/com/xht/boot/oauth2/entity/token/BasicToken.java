package com.xht.boot.oauth2.entity.token;

import lombok.Data;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.time.Instant;
import java.util.Set;

/**
 * @author xht
 **/
@Data
public class BasicToken {

    @Indexed
    private String value;

    private OAuth2AccessToken.TokenType type;

    private Instant issuedAt;

    private Instant expiresAt;

    private Boolean invalidated;

    private Set<String> scopes;

    private OAuth2TokenFormat format;

    private ClaimsHolder claims;

    private ClaimsHolder metadata;

}