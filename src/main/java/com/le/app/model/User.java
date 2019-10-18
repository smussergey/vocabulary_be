package com.le.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // according to table hibernate_sequence in db
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "First name cannot be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    // @CreatedDate
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    //@LastModifiedDate
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private List<Role> roles;

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_irregularverbs_learnt",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "irregularverb_id", referencedColumnName = "irregularverb_id")})
    private Set<IrregularVerb> irregularVerbsLearnt = new HashSet<>();

    public void addIrregularVerbToLearnt(IrregularVerb irregularVerb) {
        irregularVerbsLearnt.add(irregularVerb);
        irregularVerb.getUsers().add(this);
    }

    public void removeIrregularVerbFromLearnt(IrregularVerb irregularVerb) {
        irregularVerbsLearnt.remove(irregularVerb);
        irregularVerb.getUsers().remove(this);
    }

//    @JsonIgnore
//    @Setter(AccessLevel.PRIVATE)
//    @ManyToMany(mappedBy = "usersWhoKnowIrregularVerb", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<IrregularVerb> irregularVerbsLearnt = new HashSet<>();


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
