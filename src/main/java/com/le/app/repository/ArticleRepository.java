package com.le.app.repository;

import com.le.app.model.Article;
import com.le.app.model.dto.ArticlePreview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<ArticlePreview> findAllArticlePreviewsByTopicName(String name);
}