package com.bazinga.sa.authorizationserv.application.domain.repository;

import com.bazinga.sa.authorizationserv.application.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
