package com.bazinga.sa.authorizationserv.persistence.repository.jpa;

import com.bazinga.sa.authorizationserv.persistence.repository.model.ClientPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientJpaRepository extends JpaRepository<ClientPersistable, Long> {

    @Query("""
            SELECT c
            FROM ClientPersistable c
            WHERE c.clientId = :clientId
            """)
    Optional<ClientPersistable> findByClientId(@Param("clientId") String clientId);
}
