package com.xht.modules.admin.login.manager;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.properties.XhtConfigProperties;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.log.event.LoginRequestInfo;
import com.xht.framework.oauth2.token.response.AbstractOauth2Response;
import com.xht.modules.admin.login.dao.SysLoginLogDao;
import com.xht.modules.admin.login.domain.form.PasswordLoginForm;
import com.xht.modules.admin.login.domain.form.PhoneLoginForm;
import com.xht.modules.admin.login.entity.SysLoginLogEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 登录管理类
 *
 * @author xht
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginManager {

    private final SysLoginLogDao sysLoginLogDao;

    private final XhtConfigProperties xhtConfigProperties;

    /**
     * 保存成功登录日志
     */
    public void saveLoginLog(HttpServletRequest request, String userName, String acctToken,
                             BasicForm basicForm, AbstractOauth2Response oauth2TokenResponse) {
        try {
            SysLoginLogEntity logEntity = buildBaseLogEntity(request, userName, basicForm, oauth2TokenResponse);
            logEntity.setLoginStatus(LogStatusEnums.NORMAL);
            logEntity.setAccessToken(acctToken);
            saveLog(logEntity);
        } catch (Exception e) {
            log.error("保存登录成功日志异常 userName:{}", userName, e);
        }
    }

    /**
     * 保存登录失败日志
     */
    public void saveLoginFailLog(HttpServletRequest request, String userName, BasicForm basicForm,
                                 AbstractOauth2Response oauth2TokenResponse, String loginFailReason) {
        try {
            SysLoginLogEntity logEntity = buildBaseLogEntity(request, userName, basicForm, oauth2TokenResponse);
            logEntity.setLoginStatus(LogStatusEnums.ERROR);
            logEntity.setLoginFailReason(loginFailReason);
            saveLog(logEntity);
        } catch (Exception e) {
            log.error("保存登录失败日志异常 userName:{} reason:{}", userName, loginFailReason, e);
        }
    }

    /**
     * 构建基础登录日志实体（抽取重复代码）
     */
    private SysLoginLogEntity buildBaseLogEntity(HttpServletRequest request, String userName,
                                                 BasicForm basicForm, AbstractOauth2Response oauth2TokenResponse) {
        SysLoginLogEntity entity = new SysLoginLogEntity();
        entity.setUserName(userName);
        entity.setLoginType(getLoginType(basicForm));
        entity.setLoginResponseInfo(oauth2TokenResponse);
        entity.setLoginRequestInfo(buildLoginRequestInfo(request, basicForm));
        fillCommonInfo(request, entity);
        return entity;
    }

    /**
     * 获取登录类型（密码/短信）
     */
    private LoginTypeEnums getLoginType(BasicForm basicForm) {
        if (basicForm instanceof PasswordLoginForm) {
            return LoginTypeEnums.PASSWORD;
        }
        if (basicForm instanceof PhoneLoginForm) {
            return LoginTypeEnums.PHONE;
        }
        return null;
    }

    /**
     * 构建请求信息
     */
    private LoginRequestInfo buildLoginRequestInfo(HttpServletRequest request, BasicForm basicForm) {
        LoginRequestInfo info = new LoginRequestInfo(
                ServletUtil.getParamMap(request),
                ServletUtil.getHeaderMap(request)
        );
        info.addExtend("body", basicForm);
        return info;
    }

    /**
     * 填充公共字段
     */
    private void fillCommonInfo(HttpServletRequest request, SysLoginLogEntity entity) {
        entity.setApplicationName(SpringContextUtils.getApplicationName());
        entity.setTraceId(TraceIdUtils.getTraceId());
        entity.setAppName(xhtConfigProperties.getGlobal().getAppName());
        entity.setLoginTime(LocalDateTime.now());
        entity.setLoginIp(IpUtils.getClientIP(request));
        entity.setUserAgent(ServletUtil.getHeader(request, HttpConstants.Header.USER_AGENT.getValue()));
    }

    /**
     * 统一保存日志
     */
    private void saveLog(SysLoginLogEntity entity) {
        sysLoginLogDao.save(entity);
    }
}