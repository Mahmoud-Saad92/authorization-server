package com.bazinga.sa.authorizationserv.application.domain.model;

import org.springframework.security.core.GrantedAuthority;

public record BazingaAuthority(Authority authority) implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return authority.name();
    }
}
