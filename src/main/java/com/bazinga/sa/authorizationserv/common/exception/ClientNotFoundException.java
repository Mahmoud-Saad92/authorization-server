package com.bazinga.sa.authorizationserv.common.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class ClientNotFoundException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1512477585883397115L;

    public ClientNotFoundException(String msg) {
        super(msg);
    }

    public ClientNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
