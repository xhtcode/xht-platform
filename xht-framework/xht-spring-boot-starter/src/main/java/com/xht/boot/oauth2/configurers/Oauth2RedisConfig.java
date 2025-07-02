package com.xht.boot.oauth2.configurers;

import com.xht.boot.oauth2.convert.*;
import com.xht.boot.oauth2.feign.RemoteRegisteredClientService;
import com.xht.boot.oauth2.repository.OAuth2AuthorizationGrantAuthorizationRepository;
import com.xht.boot.oauth2.repository.OAuth2RegisteredClientRepository;
import com.xht.boot.oauth2.repository.OAuth2UserConsentRepository;
import com.xht.boot.oauth2.service.BasicRegisteredClientService;
import com.xht.boot.oauth2.service.RedisOAuth2AuthorizationConsentService;
import com.xht.boot.oauth2.service.RedisOAuth2AuthorizationService;
import com.xht.boot.oauth2.service.RedisRegisteredClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;

import java.util.Arrays;

/**
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
@Configuration
@ConditionalOnClass(value = {RedisConnectionFactory.class, OAuth2AuthorizationServerConfigurer.class})
@EnableRedisRepositories("com.xht.boot.oauth2.repository")
public class Oauth2RedisConfig {

    /**
     * redisTemplate
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return redisTemplate
     */
    @Bean(name = "oauth2RedisTemplate")
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * redisCustomConversions
     *
     * @return redisCustomConversions
     */
    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(new UsernamePasswordAuthenticationTokenToBytesConverter(),
                new BytesToUsernamePasswordAuthenticationTokenConverter(),
                new OAuth2AuthorizationRequestToBytesConverter(), new BytesToOAuth2AuthorizationRequestConverter(),
                new ClaimsHolderToBytesConverter(), new BytesToClaimsHolderConverter()));
    }

    @Bean
    public RedisRegisteredClientRepository redisRegisteredClientRepository(OAuth2RegisteredClientRepository registeredClientRepository) {
        return new RedisRegisteredClientRepository(registeredClientRepository);
    }

    @Bean
    public RedisOAuth2AuthorizationService authorizationService(RegisteredClientRepository registeredClientRepository,
                                                                OAuth2AuthorizationGrantAuthorizationRepository authorizationGrantAuthorizationRepository) {
        return new RedisOAuth2AuthorizationService(registeredClientRepository, authorizationGrantAuthorizationRepository);
    }

    @Bean
    public RedisOAuth2AuthorizationConsentService authorizationConsentService(OAuth2UserConsentRepository userConsentRepository) {
        return new RedisOAuth2AuthorizationConsentService(userConsentRepository);
    }

    @Bean
    @Order(value = 1)
    @ConditionalOnBean(value = {RemoteRegisteredClientService.class, RedisRegisteredClientRepository.class})
    public BasicRegisteredClientService basicRegisteredClientService(RemoteRegisteredClientService remoteRegisteredClientService, RedisRegisteredClientRepository redisRegisteredClientRepository) {
        return new BasicRegisteredClientService(redisRegisteredClientRepository, remoteRegisteredClientService);
    }

    @Bean
    @Order(value = 2)
    @ConditionalOnBean(JdbcTemplate.class)
    @ConditionalOnMissingBean(RegisteredClientRepository.class)
    public JdbcRegisteredClientRepository jdbcRegisteredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

}
