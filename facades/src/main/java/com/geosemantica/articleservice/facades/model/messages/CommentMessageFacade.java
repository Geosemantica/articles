package com.geosemantica.articleservice.facades.model.messages;

import java.time.Instant;
import java.util.UUID;

public interface CommentMessageFacade {
    UUID messageId();

    UUID authorId();

    String text();

    Instant createdAt();
}
