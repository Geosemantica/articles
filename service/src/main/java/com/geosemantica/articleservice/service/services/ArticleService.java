package com.geosemantica.articleservice.service.services;

import com.geosemantica.articleservice.dal.model.Article;
import com.geosemantica.articleservice.dal.repositories.ArticleRepository;
import com.geosemantica.articleservice.facades.services.EventPublisher;
import com.geosemantica.articleservice.service.model.events.ArticleCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.geosemantica.articleservice.facades.exceptions.EntityNotFoundException;
import com.geosemantica.articleservice.facades.exceptions.InsufficientAuthorityException;
import com.geosemantica.articleservice.facades.model.ArticleFacade;
import com.geosemantica.articleservice.facades.identities.ArticleIdentity;
import com.geosemantica.articleservice.facades.identities.AccountIdentity;
import com.geosemantica.articleservice.facades.services.ArticleServiceFacade;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService implements ArticleServiceFacade {
    private final ArticleRepository articleRepository;
    private final EventPublisher eventPublisher;

    @Override
    public Article createArticle(AccountIdentity accountIdentity, ArticleFacade articleFacade) {
        Article article = Article.builder()
                .title(articleFacade.getTitle())
                .text(articleFacade.getText())
                .authorId(accountIdentity.getId())
                .createdAt(Instant.now())
                .build();
        article = articleRepository.save(article);
        eventPublisher.publishEvent(new ArticleCreatedEvent(article));
        return article;
    }

    @Override
    public List<Article> getArticlesFeed(Pageable pageable) {
        return articleRepository.findAllSorted(pageable);
    }

    @Override
    public Article editArticle(ArticleIdentity articleIdentity, AccountIdentity accountIdentity, ArticleFacade articleFacade) {
        Article article = articleRepository.findById(articleIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(articleIdentity.getId(), Article.class));
        if (!article.getAuthorId().equals(accountIdentity.getId())) {
            throw new InsufficientAuthorityException("You do not have permission to edit this article.");
        }
        article.setTitle(articleFacade.getTitle());
        article.setText(articleFacade.getText());

        return articleRepository.save(article);
    }

}
