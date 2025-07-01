package com.bazinga.sa.authorizationserv.application.service;

import com.bazinga.sa.authorizationserv.application.domain.model.User;

public interface UserService {
    User getByUsername(String username);
}
