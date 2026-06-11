package com.xht.auth.security.oauth2.jwt;

import com.xht.auth.constant.AuthorizationConstant;
import com.xht.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;

/**
 * 描述： 自定义 NimbusJwtDecoder
 *
 * @author xht
 **/
@Slf4j
public record XhtNimbusJwtDecoder(JwtDecoder jwtDecoder) implements JwtDecoder {

    @Override
    public Jwt decode(String token) throws JwtException {
        if (StringUtils.startsWithIgnoreCase(token, AuthorizationConstant.XHT_TOKEN_PREFIX)) {
            return createAnonymousJwt(token);
        }
        return jwtDecoder.decode(token);
    }

    private static Jwt createAnonymousJwt(String token) {
        JwsAlgorithm jwsAlgorithm = SignatureAlgorithm.RS256;
        JwsHeader build = JwsHeader.with(jwsAlgorithm).build();
        Jwt.Builder jwtBuilder = Jwt.withTokenValue(token);
        jwtBuilder.issuer("http://www.xht.sso.com:9000");
        jwtBuilder.headers(stringObjectMap -> stringObjectMap.putAll(build.getHeaders()));
        return jwtBuilder.build();
    }

}
