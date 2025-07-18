package com.xht.framework.security.core.userdetails;

import com.xht.framework.core.utils.HttpServletUtils;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.core.strategy.UserDetailsStrategy;
import com.xht.framework.security.domain.RequestUserBO;
import com.xht.framework.security.exception.BasicAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class BasicUserDetailsService implements UserDetailsService, ApplicationContextAware {

    private final Map<LoginTypeEnums, UserDetailsStrategy> strategyMap = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest httpServletRequest = HttpServletUtils.getHttpServletRequest();
        Map<String, Object> stringObjectMap = Optional.ofNullable(httpServletRequest)
                .map(HttpServletUtils::getMapParameters)
                .orElseThrow(() -> new BasicAuthenticationException("请求失败,获取不到用户请求信息!"));
        RequestUserBO requestUserBO = RequestUserBO.builderUser(stringObjectMap);
        UserDetailsStrategy userDetailsStrategy = strategyMap.get(requestUserBO.getLoginType());
        if (Objects.isNull(userDetailsStrategy)) {
            throw new BasicAuthenticationException("无法查找用户信息 userDetailsStrategy is null.");
        }
        return userDetailsStrategy.loadUserByUsername(requestUserBO, httpServletRequest);
    }


    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, UserDetailsStrategy> beansOfType = applicationContext.getBeansOfType(UserDetailsStrategy.class);
        if (CollectionUtils.isEmpty(beansOfType)) {

        }
        beansOfType.values().forEach(strategy -> {
            strategyMap.put(strategy.loginType(), strategy);
        });
    }
}
