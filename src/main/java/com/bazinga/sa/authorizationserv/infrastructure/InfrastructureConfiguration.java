package com.bazinga.sa.authorizationserv.infrastructure;

import com.bazinga.sa.authorizationserv.infrastructure.security.configuration.SecurityConfiguration;
import org.springframework.context.annotation.Import;

@Import(SecurityConfiguration.class)
public class InfrastructureConfiguration {
}
