package com.bazinga.sa.authorizationserv.application.service.impl;

import com.bazinga.sa.authorizationserv.application.domain.model.Client;
import com.bazinga.sa.authorizationserv.application.domain.repository.ClientRepository;
import com.bazinga.sa.authorizationserv.application.service.ClientService;
import com.bazinga.sa.authorizationserv.common.exception.ClientNotFoundException;

public record ClientServiceImpl(ClientRepository clientRepository) implements ClientService {

    @Override
    public Client getByClientId(final String clientId) {
        return clientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException("client_id:[" + clientId + "] not found!!"));
    }
}
