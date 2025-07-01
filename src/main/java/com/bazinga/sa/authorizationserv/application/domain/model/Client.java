package com.bazinga.sa.authorizationserv.application.domain.model;

public record Client(Long sysClientId,
                     String clientId,
                     String secret,
                     String redirectUri,
                     String scope,
                     String authMethod,
                     String grantType) {
}
