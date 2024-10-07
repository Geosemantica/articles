package com.geosemantica.articleservice.service.model.broker;

import com.geosemantica.articleservice.facades.model.messages.ArticleMessageFacade;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ArticleMessage(
        UUID messageId,
        Long articleId,
        String title,
        Instant createdAt) implements ArticleMessageFacade {
}
