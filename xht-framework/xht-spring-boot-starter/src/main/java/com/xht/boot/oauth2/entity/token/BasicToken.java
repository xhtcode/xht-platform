package com.xht.boot.oauth2.entity.token;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

/**
 * @author xht
 **/
@Data
public class BasicToken {

    @Indexed
    private String tokenValue;

    private OAuth2AccessToken.TokenType tokenType;

    private Instant issuedAt;

    private Instant expiresAt;

    private Boolean invalidated;

    private Set<String> scopes;

    private OAuth2TokenFormat tokenFormat;

    private ClaimsHolder claims;

        public record ClaimsHolder(Map<String, Object> claims) {

        public ClaimsHolder add(String key, Object value) {
                claims.put(key, value);
                return this;
            }
        }
}