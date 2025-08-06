package com.xht.boot.oauth2.entity.token;

import java.util.Map;

public record ClaimsHolder(Map<String, Object> claims) {
    public ClaimsHolder add(String key, Object value) {
        claims.put(key, value);
        return this;
    }
}