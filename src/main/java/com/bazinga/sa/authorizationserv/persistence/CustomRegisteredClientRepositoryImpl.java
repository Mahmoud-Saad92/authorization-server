package com.bazinga.sa.authorizationserv.persistence;

import com.bazinga.sa.authorizationserv.application.domain.model.Client;
import com.bazinga.sa.authorizationserv.application.domain.repository.CustomRegisteredClientRepository;
import com.bazinga.sa.authorizationserv.common.exception.ClientNotFoundException;
import com.bazinga.sa.authorizationserv.common.util.Utility;
import com.bazinga.sa.authorizationserv.persistence.mapper.ClientPersistableMapper;
import com.bazinga.sa.authorizationserv.persistence.repository.jpa.ClientJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class CustomRegisteredClientRepositoryImpl implements CustomRegisteredClientRepository {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientPersistableMapper clientPersistableMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(RegisteredClient registeredClient) {
        Client client = Utility.fromRegisteredClient(registeredClient);

        clientJpaRepository.save(clientPersistableMapper.toClientPersistable(client));
    }

    @Override
    @Transactional(readOnly = true)
    public RegisteredClient findById(String id) {
        var client =
                clientJpaRepository
                        .findById(Long.valueOf(id))
                        .orElseThrow(() -> new ClientNotFoundException("client with id:[" + id + "] not found!!"));

        return Utility.fromClient(clientPersistableMapper.toClient(client));
    }

    @Override
    @Transactional(readOnly = true)
    public RegisteredClient findByClientId(String clientId) {
        var client =
                clientJpaRepository
                        .findByClientId(clientId)
                        .orElseThrow(() -> new ClientNotFoundException("client_id:[" + clientId + "] not found!!"));

        return Utility.fromClient(clientPersistableMapper.toClient(client));
    }
}
