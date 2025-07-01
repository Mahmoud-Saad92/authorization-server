package com.bazinga.sa.authorizationserv.persistence.repository.model;

import com.bazinga.sa.authorizationserv.common.audit.AuditingBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_BAZINGA_CLIENT")
public class ClientPersistable extends AuditingBaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 9632845631L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "secret", nullable = false)
    private String secret;

    @Column(name = "redirect_uri", nullable = false)
    private String redirectUri;

    @Column(name = "scope", nullable = false)
    private String scope;

    @Column(name = "auth_method", nullable = false)
    private String authMethod;

    @Column(name = "grant_type", nullable = false)
    private String grantType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientPersistable clientPersistable = (ClientPersistable) o;
        return Objects.equals(this.getId(), clientPersistable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}
