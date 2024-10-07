package com.geosemantica.articleservice.facades.services;

import org.springframework.data.domain.Pageable;
import com.geosemantica.articleservice.facades.model.ArticleFacade;
import com.geosemantica.articleservice.facades.identities.ArticleIdentity;
import com.geosemantica.articleservice.facades.identities.AccountIdentity;

import java.util.List;

public interface ArticleServiceFacade {
    ArticleFacade createArticle(AccountIdentity accountIdentity, ArticleFacade articleFacade);

    List<? extends ArticleFacade> getArticlesFeed(Pageable pageable);

    ArticleFacade editArticle(ArticleIdentity articleIdentity, AccountIdentity accountIdentity, ArticleFacade articleFacade);
}
