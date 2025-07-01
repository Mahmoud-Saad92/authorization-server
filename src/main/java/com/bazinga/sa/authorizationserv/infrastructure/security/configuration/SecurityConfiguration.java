package com.bazinga.sa.authorizationserv.infrastructure.security.configuration;

import com.bazinga.sa.authorizationserv.application.service.UserService;
import com.bazinga.sa.authorizationserv.application.service.impl.CustomUserDetailsServiceImpl;
import com.bazinga.sa.authorizationserv.infrastructure.security.validator.RedirectUriValidator;
import com.bazinga.sa.authorizationserv.infrastructure.security.validator.impl.RedirectUriValidatorImpl;
import com.bazinga.sa.authorizationserv.persistence.CustomRegisteredClientRepositoryImpl;
import com.bazinga.sa.authorizationserv.persistence.mapper.ClientPersistableMapper;
import com.bazinga.sa.authorizationserv.persistence.repository.jpa.ClientJpaRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class SecurityConfiguration {

    @Bean
    protected RedirectUriValidator redirectUriValidator() {
        return new RedirectUriValidatorImpl();
    }

    // http://localhost:8136/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://abcd.com/authorized&code_challenge=QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8&code_challenge_method=S256

    @Bean
    @Order(1)
    protected SecurityFilterChain configureAuthorizationServerSecurity(HttpSecurity http,
                                                                       RedirectUriValidator redirectUriValidator) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .authorizationEndpoint(auth -> auth.authenticationProviders(getAuthorizationEndpointProviders(redirectUriValidator)))
                .oidc(Customizer.withDefaults());

        http.exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webApplicationSecurity(HttpSecurity http) throws Exception {
        return http
                .formLogin(c -> c.failureForwardUrl("/login"))
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .build();
    }

    @Bean
    protected UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsServiceImpl(userService);
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    protected RegisteredClientRepository registeredClientRepository(ClientJpaRepository clientJpaRepository,
                                                                    ClientPersistableMapper clientPersistableMapper) {
        return new CustomRegisteredClientRepositoryImpl(clientJpaRepository, clientPersistableMapper);
    }

    @Bean
    protected AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
        kg.initialize(2048);
        KeyPair kp = kg.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

        RSAKey key = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet set = new JWKSet(key);
        return new ImmutableJWKSet<>(set);
    }

    @Bean
    protected OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            var authorities = context.getPrincipal().getAuthorities();

            context.getClaims().claim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).toList()); // List<String>
        };
    }

    private Consumer<List<AuthenticationProvider>> getAuthorizationEndpointProviders(RedirectUriValidator redirectUriValidator) {
        return providers -> {
            for (AuthenticationProvider provider : providers) {
                if (provider instanceof OAuth2AuthorizationCodeRequestAuthenticationProvider p) {
                    p.setAuthenticationValidator(redirectUriValidator);
                }
            }
        };
    }
}
