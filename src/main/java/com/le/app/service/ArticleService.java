package com.le.app.service;

import com.le.app.model.dto.ArticlePreview;
import com.le.app.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;

    }
    @Transactional(readOnly = true)
    public List<ArticlePreview> findAllArticlePreviewsByTopicName(String name) {
        return  articleRepository.findAllArticlePreviewsByTopicName(name);

    }
}
