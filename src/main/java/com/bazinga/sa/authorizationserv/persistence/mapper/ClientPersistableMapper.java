package com.bazinga.sa.authorizationserv.persistence.mapper;

import com.bazinga.sa.authorizationserv.application.domain.model.Client;
import com.bazinga.sa.authorizationserv.persistence.repository.model.ClientPersistable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientPersistableMapper {

    @Mapping(source = "id", target = "sysClientId")
    Client toClient(ClientPersistable clientPersistable);

    @Mapping(target = "id", ignore = true)
    ClientPersistable toClientPersistable(Client client);
}
