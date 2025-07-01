package com.bazinga.sa.authorizationserv.common.configuration;

import com.bazinga.sa.authorizationserv.application.domain.repository.ClientRepository;
import com.bazinga.sa.authorizationserv.application.domain.repository.UserRepository;
import com.bazinga.sa.authorizationserv.application.service.ClientService;
import com.bazinga.sa.authorizationserv.application.service.UserService;
import com.bazinga.sa.authorizationserv.application.service.impl.ClientServiceImpl;
import com.bazinga.sa.authorizationserv.application.service.impl.UserServiceImpl;
import com.bazinga.sa.authorizationserv.common.audit.AuditorAwareImpl;
import com.bazinga.sa.authorizationserv.infrastructure.InfrastructureConfiguration;
import com.bazinga.sa.authorizationserv.persistence.ClientRepositoryImpl;
import com.bazinga.sa.authorizationserv.persistence.UserRepositoryImpl;
import com.bazinga.sa.authorizationserv.persistence.mapper.ClientPersistableMapper;
import com.bazinga.sa.authorizationserv.persistence.mapper.UserPersistableMapper;
import com.bazinga.sa.authorizationserv.persistence.repository.jpa.ClientJpaRepository;
import com.bazinga.sa.authorizationserv.persistence.repository.jpa.UserJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;

@Configuration
@Import({InfrastructureConfiguration.class})
public class ApplicationConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    protected UserRepository userRepository(UserJpaRepository userJpaRepository,
                                            UserPersistableMapper userPersistableMapper) {
        return new UserRepositoryImpl(userJpaRepository, userPersistableMapper);
    }

    @Bean
    protected UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    protected ClientRepository clientRepository(ClientJpaRepository clientJpaRepository,
                                                ClientPersistableMapper clientPersistableMapper) {
        return new ClientRepositoryImpl(clientJpaRepository, clientPersistableMapper);
    }

    @Bean
    protected ClientService clientService(ClientRepository clientRepository) {
        return new ClientServiceImpl(clientRepository);
    }
}
