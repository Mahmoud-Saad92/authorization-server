package com.bazinga.sa.authorizationserv.persistence.mapper;

import com.bazinga.sa.authorizationserv.application.domain.model.User;
import com.bazinga.sa.authorizationserv.persistence.repository.model.UserPersistable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AuthorityPersistableMapper.class})
public interface UserPersistableMapper {

    @Mapping(source = "id", target = "sysUserId")
    User toUser(UserPersistable userPersistable);
}
