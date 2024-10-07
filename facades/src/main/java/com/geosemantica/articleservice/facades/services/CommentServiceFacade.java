package com.geosemantica.articleservice.facades.services;

import org.springframework.data.domain.Pageable;
import com.geosemantica.articleservice.facades.model.CommentFacade;
import com.geosemantica.articleservice.facades.identities.CommentIdentity;
import com.geosemantica.articleservice.facades.identities.ArticleIdentity;
import com.geosemantica.articleservice.facades.identities.AccountIdentity;

import java.util.List;

public interface CommentServiceFacade {
    CommentFacade createComment(ArticleIdentity articleIdentity, AccountIdentity accountIdentity, CommentFacade commentFacade);

    CommentFacade editComment(CommentIdentity commentIdentity, AccountIdentity accountIdentity, CommentFacade commentFacade);

    void deleteComment(CommentIdentity commentIdentity, AccountIdentity accountIdentity);

    List<? extends CommentFacade> getCommentsByArticle(ArticleIdentity articleIdentity, Pageable pageable);

    List<? extends CommentFacade> getReplies(CommentIdentity commentIdentity, Pageable pageable);
}
