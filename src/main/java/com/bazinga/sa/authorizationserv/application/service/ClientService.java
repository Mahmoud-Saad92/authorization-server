package com.bazinga.sa.authorizationserv.application.service;

import com.bazinga.sa.authorizationserv.application.domain.model.Client;

public interface ClientService {
    Client getByClientId(String clientId);
}
