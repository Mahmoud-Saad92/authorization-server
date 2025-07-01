package com.bazinga.sa.authorizationserv.infrastructure.security.validator;

import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;

import java.util.function.Consumer;

public interface RedirectUriValidator extends Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> {
}
