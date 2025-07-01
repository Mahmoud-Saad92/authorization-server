package com.bazinga.sa.authorizationserv.application.domain.model;

import java.util.Set;

public record User(Long sysUserId,
                   String username,
                   String password,
                   Set<Authority> authorities) {
}
