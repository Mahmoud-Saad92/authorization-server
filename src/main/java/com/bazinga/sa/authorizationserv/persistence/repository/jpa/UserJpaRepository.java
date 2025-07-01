package com.bazinga.sa.authorizationserv.persistence.repository.jpa;

import com.bazinga.sa.authorizationserv.persistence.repository.model.UserPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserPersistable, Long> {

    @Query("""
            SELECT u
            FROM UserPersistable u
            WHERE u.username = :username
            """)
    Optional<UserPersistable> findByUserName(@Param("username") String username);
}
