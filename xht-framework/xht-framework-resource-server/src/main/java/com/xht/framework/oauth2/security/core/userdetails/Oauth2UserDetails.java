package com.xht.framework.oauth2.security.core.userdetails;

import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

/**
 * Oauth2用户详情
 *
 * @author xht
 **/
@NoArgsConstructor
public class Oauth2UserDetails extends BasicUserDetails implements OAuth2AuthenticatedPrincipal {

    public Oauth2UserDetails(BasicUserDetails basicUserDetails) {
        this.getAttributes().putAll(basicUserDetails.getAttributes());
    }


    /**
     * 构造方法
     *
     * @return Oauth2UserDetails
     */
    @Override
    public String getName() {
        return super.getUsername();
    }


}
