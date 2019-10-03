package com.le.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "irregularverbs")
public class IrregularVerb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // according to table hibernate_sequence in db
    @Column(name = "irregularverb_id")
    private Long id;

    @NotBlank
    @Column(name = "infinitive", nullable = false)
    private String infinitive;

    @Column(name = "transcription_infinitive")
    private String transcriptionInfinitive;

    @NotBlank
    @Column(name = "past_tense", nullable = false)
    private String pastTense;

    @Column(name = "transcription_past_tense")
    private String transcriptionPastTense;

    @NotBlank
    @Column(name = "past_participle", nullable = false)
    private String pastParticiple;

    @Column(name = "transcription_past_participle")
    private String transcriptionPastParticiple;

    @NotBlank
    @Column(name = "translation", nullable = false)
    private String translation;

    @JsonIgnore
    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "irregularVerbsLearnt", fetch = FetchType.LAZY)
    private Set <User> users = new HashSet<>();

//    @JsonIgnore
//    @Setter(AccessLevel.PRIVATE)
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "users_irregularverbs_learnt",
//            joinColumns = {@JoinColumn(name = "irregularverb_id", referencedColumnName = "irregularverb_id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")})
//    private Set<User> usersWhoKnowIrregularVerb = new HashSet<>();
//
//    public void addUser(User user) {
//        usersWhoKnowIrregularVerb.add(user);
//        user.getIrregularVerbsLearnt().add(this);
//    }
//
//    public void removeUser(User user) {
//        usersWhoKnowIrregularVerb.remove(user);
//    }

}