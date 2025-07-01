package com.bazinga.sa.authorizationserv.persistence;

import com.bazinga.sa.authorizationserv.application.domain.model.Client;
import com.bazinga.sa.authorizationserv.application.domain.repository.ClientRepository;
import com.bazinga.sa.authorizationserv.persistence.mapper.ClientPersistableMapper;
import com.bazinga.sa.authorizationserv.persistence.repository.jpa.ClientJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientPersistableMapper clientPersistableMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findByClientId(String clientId) {
        return clientJpaRepository
                .findByClientId(clientId)
                .map(clientPersistableMapper::toClient);
    }
}
