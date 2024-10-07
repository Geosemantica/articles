package com.geosemantica.articleservice.facades.model.messages;

import java.time.Instant;
import java.util.UUID;

public interface ArticleMessageFacade {
    UUID messageId();

    Long articleId();

    String title();

    Instant createdAt();
}
