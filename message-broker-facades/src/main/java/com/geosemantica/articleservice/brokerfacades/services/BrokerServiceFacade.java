package com.geosemantica.articleservice.brokerfacades.services;

import com.geosemantica.articleservice.facades.model.messages.ArticleMessageFacade;
import com.geosemantica.articleservice.facades.model.messages.CommentMessageFacade;

public interface BrokerServiceFacade {
    void sendNewArticle(ArticleMessageFacade articleMessage);

    void sendNewComment(CommentMessageFacade commentMessage);
}
