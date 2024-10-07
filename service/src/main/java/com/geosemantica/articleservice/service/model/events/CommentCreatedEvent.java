package com.geosemantica.articleservice.service.model.events;

import com.geosemantica.articleservice.dal.model.Comment;
import com.geosemantica.articleservice.facades.events.Event;

public record CommentCreatedEvent(Comment comment) implements Event {
}
