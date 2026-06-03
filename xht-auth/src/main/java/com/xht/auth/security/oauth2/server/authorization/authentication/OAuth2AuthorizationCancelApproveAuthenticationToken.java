package com.xht.auth.security.oauth2.server.authorization.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 描述：
 *
 * @author xht
 **/
public class OAuth2AuthorizationCancelApproveAuthenticationToken extends AbstractAuthenticationToken {

    private final static String error = "?error=access_denied&error_description=用户或服务器拒绝了请求";

    private final String redirectUri;

    private final Object principal;

    public OAuth2AuthorizationCancelApproveAuthenticationToken(String redirectUri, Object principal) {
        super(null);
        this.redirectUri = redirectUri;
        this.principal = principal;
        setAuthenticated(true);
    }


    /**
     * The credentials that prove the principal is correct. This is usually a password,
     * but could be anything relevant to the <code>AuthenticationManager</code>. Callers
     * are expected to populate the credentials.
     *
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getRedirectUri() {
        return redirectUri + error;
    }
}
