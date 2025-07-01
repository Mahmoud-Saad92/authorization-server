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
@Table(name = "TBL_BAZINGA_AUTHORITY")
public class AuthorityPersistable extends AuditingBaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 6912753631L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "authorities",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserPersistable> users;

    public AuthorityPersistable() {
        this.users = new HashSet<>();
    }

    public void addUser(UserPersistable user) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
        user.getAuthorities().add(this);
    }

    public void removeUser(UserPersistable user) {
        if (users != null) {
            users.remove(user);
            user.getAuthorities().remove(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityPersistable authorityPersistable = (AuthorityPersistable) o;
        return Objects.equals(this.getId(), authorityPersistable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}
