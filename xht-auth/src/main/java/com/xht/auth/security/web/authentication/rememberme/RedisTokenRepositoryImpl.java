package com.xht.auth.security.web.authentication.rememberme;

import com.xht.auth.configuration.properties.RememberMeProperties;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Token存储
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisTokenRepositoryImpl implements PersistentTokenRepository {

    private final static String REDIS_KEY_PREFIX = "xht:rememberMe";

    private final RedisRepository redisRepository;

    private final RememberMeProperties rememberMeProperties;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String username = token.getUsername();
        String series = token.getSeries();
        redisRepository.set(REDIS_KEY_PREFIX + ":" + username, series, rememberMeProperties.getTokenValiditySeconds(), TimeUnit.SECONDS);
        redisRepository.set(REDIS_KEY_PREFIX + ":" + series, token, rememberMeProperties.getTokenValiditySeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = redisRepository.get(REDIS_KEY_PREFIX + ":" + series);
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed);
        createNewToken(newToken);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return redisRepository.get(REDIS_KEY_PREFIX + ":" + seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        String seriesId = redisRepository.get(REDIS_KEY_PREFIX + ":" + username);
        if (StringUtils.hasText(seriesId)) {
            redisRepository.delete(REDIS_KEY_PREFIX + ":" + seriesId);
        }
        redisRepository.delete(REDIS_KEY_PREFIX + ":" + username);
    }


}
