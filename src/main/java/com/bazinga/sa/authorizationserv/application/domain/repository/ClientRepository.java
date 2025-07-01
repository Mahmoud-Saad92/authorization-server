package com.bazinga.sa.authorizationserv.application.domain.repository;

import com.bazinga.sa.authorizationserv.application.domain.model.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByClientId(String clientId);
}
