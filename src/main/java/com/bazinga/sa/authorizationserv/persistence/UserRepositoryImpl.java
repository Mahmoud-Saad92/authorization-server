package com.bazinga.sa.authorizationserv.persistence;

import com.bazinga.sa.authorizationserv.application.domain.model.User;
import com.bazinga.sa.authorizationserv.application.domain.repository.UserRepository;
import com.bazinga.sa.authorizationserv.persistence.mapper.UserPersistableMapper;
import com.bazinga.sa.authorizationserv.persistence.repository.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistableMapper userPersistableMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userJpaRepository
                .findByUserName(username)
                .map(userPersistableMapper::toUser);
    }
}
