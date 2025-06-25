package com.xht.auth.captcha.authentication.dao;

import com.xht.auth.captcha.exception.CaptchaException;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.constant.enums.LoginTypeEnums;
import com.xht.auth.domain.RequestUserBO;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.web.utils.HttpServletUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xht
 **/
@Slf4j
@Component
public class CaptchaAuthenticationProvider extends DaoAuthenticationProvider {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ICaptchaService iCaptchaService;

    /**
     * 利用构造方法在通过{@link Component}注解初始化时
     * 注入UserDetailsService和passwordEncoder，然后
     * 设置调用父类关于这两个属性的set方法设置进去
     *
     * @param userDetailsService 用户服务，给框架提供用户信息
     * @param passwordEncoder    密码解析器，用于加密和校验密码
     */
    public CaptchaAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Authenticate captcha...");
        // 获取当前request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException("Failed to get the current request.");
        }
        HttpServletRequest httpServletRequest = HttpServletUtils.getHttpServletRequest().orElseThrow(() -> new RuntimeException("Failed to get the current request"));
        Map<String, Object> mapParameters = HttpServletUtils.getMapParameters(httpServletRequest);
        RequestUserBO requestUserBO = RequestUserBO.builderUser(mapParameters);
        requestUserBO.checkLoginType();//todo 验证码校验逻辑 requestUserBO.getLoginType()
        if (Objects.equals(LoginTypeEnums.CODE, requestUserBO.getLoginType())) {
            String captcha = requestUserBO.getCaptcha();
            try {
                if (StringUtils.isEmpty(captcha)) {
                    throw new CaptchaException("请输入验证码.");
                }
                // 验证码校验逻辑
                String captchaId = requestUserBO.getCaptchaKey();
                if (StringUtils.isEmpty(captchaId)) {
                    throw new CaptchaException("验证码已失效.");
                }
                captchaId = requestUserBO.generateCaptchaKey();
                Long expire = stringRedisTemplate.getExpire(captchaId, TimeUnit.SECONDS);
                if (expire <= 0) {
                    throw new CaptchaException("验证码已过期.");
                }
                String captchaCode = stringRedisTemplate.opsForValue().get(captchaId);
                if (!StringUtils.equalsIgnoreCase(captchaCode, captcha)) {
                    throw new CaptchaException("验证码错误.");
                }
            } catch (CaptchaException e) {
                throw new CaptchaException(e.getMessage());
            } catch (Exception e) {
                log.error("验证码认证失败.", e);
                throw new CaptchaException("验证码认证失败.");
            } finally {
                iCaptchaService.removeCaptcha(requestUserBO.getCaptchaKey());
            }
            return super.authenticate(authentication);
        }
        return super.authenticate(authentication);
    }


}
