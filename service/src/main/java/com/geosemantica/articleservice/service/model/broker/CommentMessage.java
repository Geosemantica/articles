package com.geosemantica.articleservice.service.model.broker;

import com.geosemantica.articleservice.facades.model.messages.CommentMessageFacade;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CommentMessage(
        UUID messageId,
        UUID authorId,
        String text,
        Instant createdAt) implements CommentMessageFacade {
}
