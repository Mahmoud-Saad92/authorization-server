package com.bazinga.sa.authorizationserv.persistence.mapper;

import com.bazinga.sa.authorizationserv.application.domain.model.Authority;
import com.bazinga.sa.authorizationserv.persistence.repository.model.AuthorityPersistable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityPersistableMapper {

    @Mapping(source = "id", target = "sysAuthorityId")
    Authority toAuthority(AuthorityPersistable authorityPersistable);
}
