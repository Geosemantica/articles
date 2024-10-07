package com.geosemantica.articleservice.service.services;

import com.geosemantica.articleservice.dal.model.Article;
import com.geosemantica.articleservice.dal.model.Comment;
import com.geosemantica.articleservice.service.model.broker.ArticleMessage;
import com.geosemantica.articleservice.service.model.broker.CommentMessage;
import com.geosemantica.articleservice.service.model.events.ArticleCreatedEvent;
import com.geosemantica.articleservice.service.model.events.CommentCreatedEvent;
import com.geosemantica.articleservice.brokerfacades.services.BrokerServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final BrokerServiceFacade brokerService;
    private final IdentityService identityService;

    @EventListener
    private void handleArticleCreated(ArticleCreatedEvent event) {
        Article article = event.article();
        ArticleMessage message = ArticleMessage.builder()
                .messageId(identityService.newUuid())
                .articleId(article.getId())
                .title(article.getTitle())
                .createdAt(article.getCreatedAt())
                .build();
        brokerService.sendNewArticle(message);
    }

    @EventListener
    private void handleCommentCreated(CommentCreatedEvent event) {
        Comment comment = event.comment();
        CommentMessage message = CommentMessage.builder()
                .messageId(identityService.newUuid())
                .authorId(comment.getAuthorId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
        brokerService.sendNewComment(message);
    }
}
