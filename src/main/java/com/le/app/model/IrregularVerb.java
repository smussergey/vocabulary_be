package com.le.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
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

    @NotBlank(message = "Infinitive cannot be blank")
    @Column(name = "infinitive", nullable = false)
    private String infinitive;

    @Column(name = "transcription_infinitive")
    private String transcriptionInfinitive;

    @NotBlank(message = "Past tense cannot be blank")
    @Column(name = "past_tense", nullable = false)
    private String pastTense;

    @Column(name = "transcription_past_tense")
    private String transcriptionPastTense;

    @NotBlank(message = "Past Participle cannot be blank")
    @Column(name = "past_participle", nullable = false)
    private String pastParticiple;

    @Column(name = "transcription_past_participle")
    private String transcriptionPastParticiple;

    @NotBlank(message = "Translation cannot be blank")
    @Column(name = "translation", nullable = false)
    private String translation;

    @JsonIgnore
    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "irregularVerbsLearnt", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

}