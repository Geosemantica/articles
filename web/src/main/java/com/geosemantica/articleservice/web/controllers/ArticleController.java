package com.geosemantica.articleservice.web.controllers;

import com.geosemantica.articleservice.facades.identities.AccountIdentity;
import com.geosemantica.articleservice.facades.model.ArticleFacade;
import com.geosemantica.articleservice.facades.services.ArticleServiceFacade;
import com.geosemantica.articleservice.web.config.annotations.AllowFor;
import com.geosemantica.articleservice.web.config.annotations.AllowForAdmins;
import com.geosemantica.articleservice.web.config.annotations.AllowForAll;
import com.geosemantica.articleservice.web.model.requests.ArticleRequest;
import com.geosemantica.articleservice.web.model.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleServiceFacade articleService;

    @AllowForAdmins
    @PostMapping
    public ResponseEntity<?> createArticle(
            @Validated @RequestBody ArticleRequest request,
            @AuthenticationPrincipal AccountIdentity accountIdentity) {
        ArticleFacade factor = articleService.createArticle(accountIdentity, request);
        return ApiResponse.created(factor);
    }

    @AllowForAll
    @GetMapping("/feed")
    public ResponseEntity<?> getFeed(Pageable pageable) {
        List<? extends ArticleFacade> articlesFeed = articleService.getArticlesFeed(pageable);
        return ApiResponse.success(articlesFeed);
    }

    @AllowForAdmins
    @PutMapping("/{articleId}")
    public ResponseEntity<?> editArticle(
            @PathVariable Long articleId,
            @Validated @RequestBody ArticleRequest request,
            @AuthenticationPrincipal AccountIdentity accountIdentity) {
        ArticleFacade article = articleService.editArticle(() -> articleId, accountIdentity, request);
        return ApiResponse.success(article);
    }
}
