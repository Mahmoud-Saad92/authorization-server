package com.bazinga.sa.authorizationserv.infrastructure.security.validator.impl;

import com.bazinga.sa.authorizationserv.infrastructure.security.validator.RedirectUriValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Objects;

public class RedirectUriValidatorImpl implements RedirectUriValidator {

    @Override
    public void accept(OAuth2AuthorizationCodeRequestAuthenticationContext context) {
        OAuth2AuthorizationCodeRequestAuthenticationToken authentication = context.getAuthentication();

        if (Objects.nonNull(authentication)) {
            String redirectUri = authentication.getRedirectUri();

            RegisteredClient registeredClient = context.getRegisteredClient();

            if (!registeredClient.getRedirectUris().contains(redirectUri)) {
                OAuth2Error oAuth2Error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "Invalid redirect_uri, please try again with a valid data.", redirectUri);
                throw new OAuth2AuthorizationCodeRequestAuthenticationException(oAuth2Error, authentication);
            }
        }
    }
}
