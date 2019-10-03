package com.le.app.service;

import com.le.app.dto.ArticleDto;
import com.le.app.model.Article;
import com.le.app.model.Topic;
import com.le.app.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private TopicService topicService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, TopicService topicService) {
        this.articleRepository = articleRepository;
        this.topicService = topicService;
    }

    public List<ArticleDto> findAllArticleDtosByTopicName(String name) {
        Topic topic = topicService.findByName(name);
        List<Article> articles = articleRepository.findAllByTopic(topic);
        List<ArticleDto> result = articles.stream()
                .map(ArticleDto::fromArticle)
                .collect(Collectors.toList());
        return result;
    }
}
