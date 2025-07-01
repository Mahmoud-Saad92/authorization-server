package com.bazinga.sa.authorizationserv.persistence.repository.model;

import com.bazinga.sa.authorizationserv.common.audit.AuditingBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "TBL_BAZINGA_USER")
public class UserPersistable extends AuditingBaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5812823629L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tbl_bazinga_users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"),
            uniqueConstraints = @UniqueConstraint(name = "tbl_bazinga_users_authorities_user_authority_uk",
                    columnNames = {"user_id", "authority_id"})
    )
    private Set<AuthorityPersistable> authorities;

    public UserPersistable() {
        this.authorities = new HashSet<>();
    }

    public void addAuthority(AuthorityPersistable authority) {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
        authority.getUsers().add(this);
    }

    public void removeAuthority(AuthorityPersistable authority) {
        if (authorities != null) {
            authorities.remove(authority);
            authority.getUsers().remove(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPersistable userPersistable = (UserPersistable) o;
        return Objects.equals(this.getId(), userPersistable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}
