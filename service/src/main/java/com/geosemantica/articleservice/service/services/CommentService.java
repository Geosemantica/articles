package com.geosemantica.articleservice.service.services;

import com.geosemantica.articleservice.dal.model.Comment;
import com.geosemantica.articleservice.dal.model.Article;
import com.geosemantica.articleservice.dal.repositories.CommentRepository;
import com.geosemantica.articleservice.dal.repositories.ArticleRepository;
import com.geosemantica.articleservice.facades.services.EventPublisher;
import com.geosemantica.articleservice.service.model.events.CommentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.geosemantica.articleservice.facades.exceptions.EntityNotFoundException;
import com.geosemantica.articleservice.facades.exceptions.InsufficientAuthorityException;
import com.geosemantica.articleservice.facades.model.CommentFacade;
import com.geosemantica.articleservice.facades.identities.CommentIdentity;
import com.geosemantica.articleservice.facades.identities.ArticleIdentity;
import com.geosemantica.articleservice.facades.identities.AccountIdentity;
import com.geosemantica.articleservice.facades.services.CommentServiceFacade;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentService implements CommentServiceFacade {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final EventPublisher eventPublisher;

    @Override
    public Comment createComment(
            ArticleIdentity articleIdentity,
            AccountIdentity accountIdentity,
            CommentFacade commentFacade) {
        Article article = articleRepository.findById(articleIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(articleIdentity.getId(), Article.class));
        if (commentFacade.getReplyTo() != null && !commentRepository.existsById(commentFacade.getReplyTo())) {
            throw new EntityNotFoundException(commentFacade.getReplyTo(), Comment.class);
        }
        Comment comment = Comment.builder()
                .text(commentFacade.getText())
                .article(article)
                .replyTo(commentFacade.getReplyTo())
                .authorId(accountIdentity.getId())
                .createdAt(Instant.now())
                .build();

        comment = commentRepository.save(comment);
        eventPublisher.publishEvent(new CommentCreatedEvent(comment));
        return comment;
    }

    @Override
    public Comment editComment(
            CommentIdentity commentIdentity,
            AccountIdentity accountIdentity,
            CommentFacade commentFacade) {
        Comment comment = commentRepository.findById(commentIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(commentIdentity.getId(), Comment.class));
        if (!comment.getAuthorId().equals(accountIdentity.getId())) {
            throw new InsufficientAuthorityException("You do not have permission to edit this comment.");
        }
        comment.setText(commentFacade.getText());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(CommentIdentity commentIdentity, AccountIdentity accountIdentity) {
        Comment comment = commentRepository.findById(commentIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(commentIdentity.getId(), Comment.class));
        if (!comment.getAuthorId().equals(accountIdentity.getId())) {
            throw new InsufficientAuthorityException("You do not have permission to delete this comment.");
        }
        int affected = commentRepository.setRemoved(comment);
        if (affected != 1) {
            throw new EntityNotFoundException(comment.getId(), Comment.class);
        }
    }

    @Override
    public List<Comment> getCommentsByArticle(ArticleIdentity articleIdentity, Pageable pageable) {
        Article article = articleRepository.findById(articleIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(articleIdentity.getId(), Article.class));
        return commentRepository.findByArticle(article, pageable);
    }

    @Override
    public List<Comment> getReplies(CommentIdentity commentIdentity, Pageable pageable) {
        Comment comment = commentRepository.findById(commentIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(commentIdentity.getId(), Comment.class));
        return commentRepository.findReplies(comment.getId(), pageable);
    }


    public void removeAllComments(AccountIdentity accountIdentity) {
        int affected = commentRepository.setRemovedAll(accountIdentity.getId());
        log.warn("{} comments have been removed by user id {}", affected, accountIdentity.getId());
    }

}
