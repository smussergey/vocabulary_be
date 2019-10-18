package com.le.app.rest;

import com.le.app.model.dto.ArticlePreview;
import com.le.app.security.jwt.JwtTokenProvider;
import com.le.app.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ArticleRestController {
    private final ArticleService articleService;

    @Autowired
    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/content/articles/{topicname}")
    public ResponseEntity<List<ArticlePreview>> getAllArticlesPreviewsByTopic(@PathVariable(name = "topicname") String name) {
        return ResponseEntity.ok(articleService.findAllArticlePreviewsByTopicName(name));
    }
}
