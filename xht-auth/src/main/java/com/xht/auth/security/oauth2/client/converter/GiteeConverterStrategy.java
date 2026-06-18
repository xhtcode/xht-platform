package com.xht.auth.security.oauth2.client.converter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.xht.auth.authentication.dao.IOauth2ThirdAccountDao;
import com.xht.auth.authentication.entity.Oauth2ThirdAccountEntity;
import com.xht.auth.constant.Oauth2PlatformEnums;
import com.xht.framework.jackson.JsonUtils;
import com.xht.framework.security.constant.Oauth2BindStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 描述：码云登录实现 
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class GiteeConverterStrategy extends Oauth2UserConverterStrategy {

    private final IOauth2ThirdAccountDao oauth2ThirdAccountDao;

    /**
     * 将 OAuth2 用户信息转换为目标对象。
     * <p>
     * 子类应实现此方法，根据第三方 OAuth2 平台返回的用户请求和原始用户信息，
     * 提取并映射为业务所需的用户对象（如本地用户实体、DTO 等）。
     *
     * @param userRequest 三方登录请求
     * @param oAuth2User  三方登录用户信息
     * @return 转换后的业务用户对象
     */
    @Override
    public Oauth2ThirdAccountEntity convert(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.debug("attributes: {}", JsonUtils.toJsonString(attributes));
        Oauth2ThirdAccountEntity dbAccountEntity = oauth2ThirdAccountDao.findByOpenid(oAuth2User.getName());
        if (Objects.isNull(dbAccountEntity)) {
            dbAccountEntity = new Oauth2ThirdAccountEntity();
            dbAccountEntity.setUserId(IdUtil.getSnowflakeNextId());
            dbAccountEntity.setOpenId(oAuth2User.getName());
            dbAccountEntity.setPlatformType(Oauth2PlatformEnums.GITEE.getValue());
            dbAccountEntity.setNickName(MapUtil.getStr(attributes, "login"));
            dbAccountEntity.setUserAvatar(MapUtil.getStr(attributes, "avatar_url"));
            dbAccountEntity.setBindStatus(Oauth2BindStatus.BIND);
            oauth2ThirdAccountDao.save(dbAccountEntity);
        }
        return dbAccountEntity;
    }

    /**
     * 判断当前策略是否支持指定的 OAuth2 客户端注册。
     * <p>
     * 子类通过比对 {@code oauth2PlatformEnums}（如 "github"、"gitee"、"wechat" 等）
     * 来决定是否由本策略处理对应的用户信息转换逻辑。
     *
     * @param oauth2PlatformEnums OAuth2 客户端注册标识，对应配置中的 registration-id
     * @return 若当前策略支持该注册标识则返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public boolean supports(Oauth2PlatformEnums oauth2PlatformEnums) {
        return Oauth2PlatformEnums.GITEE.equals(oauth2PlatformEnums);
    }


}
