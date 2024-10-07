package com.geosemantica.articleservice.facades.model;

import com.geosemantica.articleservice.facades.identities.CommentIdentity;

import java.time.Instant;
import java.util.UUID;

public interface CommentFacade extends CommentIdentity {
    Long getReplyTo();

    String getText();

    UUID getAuthorId();

    Instant getCreatedAt();
}
