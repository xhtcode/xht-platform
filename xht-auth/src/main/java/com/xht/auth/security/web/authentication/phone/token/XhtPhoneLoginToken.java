package com.xht.auth.security.web.authentication.phone.token;

import com.xht.auth.security.web.authentication.AbstractXhtAuthenticationToken;
import com.xht.framework.core.enums.LoginTypeEnums;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 表单登录认证令牌
 * <p>
 * 该类是Spring Security认证框架中用于表单登录的AuthenticationToken实现，
 * 封装了用户主体（principal）、凭证（credentials）和权限信息（authorities）。
 * 该类支持两种状态：
 * <ul>
 *     <li>未认证状态：在用户提交登录表单后创建，包含用户名和密码</li>
 *     <li>已认证状态：在认证成功后创建，包含用户信息和权限列表</li>
 * </ul>
 * 使用静态工厂方法{@link #unauthenticated(Object, Object)}和{@link #authenticated(Object, Object, Collection)}来创建实例。
 * </p>
 *
 * @author xht
 **/
@Slf4j
@Getter
public class XhtPhoneLoginToken extends AbstractXhtAuthenticationToken {

    /**
     * 用户主体信息，通常是用户名或用户详情对象
     */
    private final Object principal;

    /**
     * 用户凭证信息，通常是密码或其他认证凭据
     */
    private Object credentials;

    /**
     * 构造未认证的表单登录令牌
     * <p>
     * 该构造函数创建一个未认证状态的令牌，用于存储用户提交的登录信息。
     * 认证状态被设置为false，权限列表为空。
     * </p>
     *
     * @param principal   用户主体信息（通常是用户名）
     * @param credentials 用户凭证信息（通常是密码）
     */
    public XhtPhoneLoginToken(Object principal, Object credentials) {
        super(LoginTypeEnums.PHONE, null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 构造已认证的表单登录令牌
     * <p>
     * 该构造函数创建一个已认证状态的令牌，包含完整的用户信息和权限列表。
     * 认证状态被设置为true，并持有用户的授权信息。
     * </p>
     * <p>
     * 注意：这里必须调用super.setAuthenticated()而不是this.setAuthenticated()，
     * 因为子类可能重写了setAuthenticated方法来防止直接修改认证状态。
     * </p>
     *
     * @param principal   用户主体信息（通常是用户详情对象）
     * @param credentials 用户凭证信息
     * @param authorities 用户拥有的权限集合
     */
    public XhtPhoneLoginToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(LoginTypeEnums.PHONE, authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    /**
     * 创建未认证状态的表单登录令牌的静态工厂方法
     * <p>
     * 该方法提供了一种更清晰的方式来创建未认证的令牌实例，
     * 通常在用户提交登录表单时调用。
     * </p>
     *
     * @param principal   用户主体信息（通常是用户名）
     * @param credentials 用户凭证信息（通常是密码）
     * @return 未认证状态的XhtFormLoginToken实例
     */
    public static XhtPhoneLoginToken unauthenticated(Object principal, Object credentials) {
        return new XhtPhoneLoginToken(principal, credentials);
    }

    /**
     * 创建已认证状态的表单登录令牌的静态工厂方法
     * <p>
     * 该方法提供了一种更清晰的方式来创建已认证的令牌实例，
     * 通常在认证成功后由AuthenticationProvider返回。
     * </p>
     *
     * @param principal   用户主体信息（通常是用户详情对象）
     * @param credentials 用户凭证信息
     * @param authorities 用户拥有的权限集合
     * @return 已认证状态的XhtFormLoginToken实例
     */
    public static XhtPhoneLoginToken authenticated(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        return new XhtPhoneLoginToken(principal, credentials, authorities);
    }

    /**
     * 清除敏感凭证信息
     * <p>
     * 该方法在认证成功后调用，用于清除存储在令牌中的敏感信息（如密码），
     * 以防止凭证信息在内存中泄露。
     * 首先调用父类的eraseCredentials()方法清除其他可能的敏感信息，
     * 然后将本地的credentials字段设置为null。
     * </p>
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

}
