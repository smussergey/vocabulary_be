package com.le.app.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // according to table hibernate_sequence in db
    @Column(name = "article_id")
    private Long id;

    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank
    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @Column(name = "author")
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;


}