package com.le.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.le.app.model.Article;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {
    private Long id;
    private String title;
    private String description;

    public static ArticleDto fromArticle(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setDescription(article.getDescription());
        return articleDto;
    }
}
