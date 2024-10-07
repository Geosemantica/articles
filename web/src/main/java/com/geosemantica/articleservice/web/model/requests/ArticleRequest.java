package com.geosemantica.articleservice.web.model.requests;

import com.geosemantica.articleservice.facades.model.ArticleFacade;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ArticleRequest implements ArticleFacade {
    @Length(max = 255)
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private Long id;
    private UUID authorId;
    private Instant createdAt;
}
