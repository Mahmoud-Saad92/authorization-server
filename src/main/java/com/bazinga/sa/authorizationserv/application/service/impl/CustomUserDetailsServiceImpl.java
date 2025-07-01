package com.bazinga.sa.authorizationserv.application.service.impl;

import com.bazinga.sa.authorizationserv.application.domain.model.BazingaUser;
import com.bazinga.sa.authorizationserv.application.domain.model.User;
import com.bazinga.sa.authorizationserv.application.service.CustomUserDetailsService;
import com.bazinga.sa.authorizationserv.application.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public record CustomUserDetailsServiceImpl(UserService userService) implements CustomUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);

        return new BazingaUser(user);
    }
}
