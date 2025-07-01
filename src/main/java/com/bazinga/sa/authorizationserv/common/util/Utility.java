package com.bazinga.sa.authorizationserv.common.util;

import com.bazinga.sa.authorizationserv.application.domain.model.Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Objects;

public interface Utility {

    static Client fromRegisteredClient(RegisteredClient registeredClient) {
        return new Client(null,
                registeredClient.getClientId(),
                registeredClient.getClientSecret(),
                registeredClient.getRedirectUris().stream().findAny().orElse(null),
                registeredClient.getScopes().stream().findAny().orElse(null),
                Objects.requireNonNull(registeredClient.getClientAuthenticationMethods().stream().findAny().orElse(null)).getValue(),
                Objects.requireNonNull(registeredClient.getAuthorizationGrantTypes().stream().findAny().orElse(null)).getValue());
    }

    static RegisteredClient fromClient(Client client) {
        return RegisteredClient.withId(String.valueOf(client.sysClientId()))
                .clientId(client.clientId())
                .clientSecret(client.secret())
                .scope(client.scope())
                .redirectUri(client.redirectUri())
                .clientAuthenticationMethod(new ClientAuthenticationMethod(client.authMethod()))
                .authorizationGrantType(new AuthorizationGrantType(client.grantType()))
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();
    }
}
