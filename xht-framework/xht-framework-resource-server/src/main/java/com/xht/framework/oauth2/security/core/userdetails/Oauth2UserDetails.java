package com.xht.framework.oauth2.security.core.userdetails;

import com.xht.framework.security.core.userdetails.BasicUserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;


/**
 * Oauth2用户详情
 *
 * @author xht
 **/
public class Oauth2UserDetails extends BasicUserDetails implements OAuth2AuthenticatedPrincipal {

    public Oauth2UserDetails(BasicUserDetails basicUserDetails) {
        super(basicUserDetails.getUserId(),
                basicUserDetails.getDeptId(),
                basicUserDetails.getUsername(),
                basicUserDetails.getPassword(),
                basicUserDetails.getMobile(),
                true,
                basicUserDetails.getAuthorities());
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
