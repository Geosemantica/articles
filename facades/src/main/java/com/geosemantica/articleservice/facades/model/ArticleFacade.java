package com.geosemantica.articleservice.facades.model;

import java.time.Instant;
import java.util.UUID;

public interface ArticleFacade {
    Long getId();

    String getTitle();

    String getText();

    UUID getAuthorId();

    Instant getCreatedAt();
}
