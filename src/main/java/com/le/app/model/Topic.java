package com.le.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // according to table hibernate_sequence in db
    @Column(name = "topic_id")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY,  cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    public void addArticle(Article article) {
        articles.add(article);
        article.setTopic(this);
    }

    public void addArticles(List<Article> articles) {
        articles.addAll(articles);
        articles.forEach(article -> article.setTopic(this));
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        article.setTopic(null);
    }
}
