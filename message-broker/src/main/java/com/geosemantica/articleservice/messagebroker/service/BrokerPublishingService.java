package com.geosemantica.articleservice.messagebroker.service;

import com.geosemantica.articleservice.facades.model.messages.ArticleMessageFacade;
import com.geosemantica.articleservice.facades.model.messages.CommentMessageFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.geosemantica.articleservice.messagebroker.config.PublishingProperties;
import com.geosemantica.articleservice.brokerfacades.services.BrokerServiceFacade;

@RequiredArgsConstructor
@Service
public class BrokerPublishingService implements BrokerServiceFacade {
    private final RabbitTemplate rabbitTemplate;

    private final PublishingProperties publishingProperties;

    @Override
    public void sendNewArticle(ArticleMessageFacade articleMessage) {
        rabbitTemplate.convertAndSend(
                publishingProperties.getArticlesExchange(),
                publishingProperties.getArticlesRoutingKey(),
                articleMessage);
    }

    @Override
    public void sendNewComment(CommentMessageFacade commentMessage) {
        rabbitTemplate.convertAndSend(
                publishingProperties.getCommentsExchange(),
                publishingProperties.getCommentsRoutingKey(),
                commentMessage);
    }
}
