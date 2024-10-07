package com.geosemantica.articleservice.service.model.events;

import com.geosemantica.articleservice.dal.model.Article;
import com.geosemantica.articleservice.facades.events.Event;

public record ArticleCreatedEvent(Article article) implements Event {
}
