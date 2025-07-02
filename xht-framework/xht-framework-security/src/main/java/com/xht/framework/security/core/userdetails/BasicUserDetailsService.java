package com.xht.framework.security.core.userdetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class BasicUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BasicUserDetails userDetails = new BasicUserDetails();
        userDetails.setUsername("admin");
        userDetails.setPassword("123456");
        userDetails.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        return userDetails;
    }
}
