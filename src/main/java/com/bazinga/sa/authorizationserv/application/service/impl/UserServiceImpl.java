package com.bazinga.sa.authorizationserv.application.service.impl;

import com.bazinga.sa.authorizationserv.application.domain.model.User;
import com.bazinga.sa.authorizationserv.application.domain.repository.UserRepository;
import com.bazinga.sa.authorizationserv.application.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public record UserServiceImpl(UserRepository userRepository) implements UserService {

    @Override
    public User getByUsername(final String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user_name:[" + username + "] not found!!"));
    }
}
