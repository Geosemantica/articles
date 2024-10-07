package com.geosemantica.articleservice.web.controllers;

import com.geosemantica.articleservice.facades.identities.AccountIdentity;
import com.geosemantica.articleservice.facades.model.CommentFacade;
import com.geosemantica.articleservice.facades.services.CommentServiceFacade;
import com.geosemantica.articleservice.web.config.annotations.AllowForAll;
import com.geosemantica.articleservice.web.config.annotations.AllowForUsers;
import com.geosemantica.articleservice.web.model.requests.CommentRequest;
import com.geosemantica.articleservice.web.model.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentServiceFacade commentService;

    @AllowForUsers
    @PostMapping("/articles/{articleId}/comment")
    public ResponseEntity<?> createComment(
            @PathVariable Long articleId,
            @Validated @RequestBody CommentRequest request,
            @AuthenticationPrincipal AccountIdentity accountIdentity) {
        CommentFacade comment = commentService.createComment(() -> articleId, accountIdentity, request);
        return ApiResponse.created(comment);
    }

    @AllowForUsers
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> editComment(
            @PathVariable Long commentId,
            @Validated @RequestBody CommentRequest request,
            @AuthenticationPrincipal AccountIdentity accountIdentity) {
        CommentFacade comment = commentService.editComment(() -> commentId, accountIdentity, request);
        return ApiResponse.success(comment);
    }

    @AllowForUsers
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal AccountIdentity accountIdentity) {
        commentService.deleteComment(() -> commentId, accountIdentity);
        return ApiResponse.success();
    }

    @AllowForAll
    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<?> getByArticle(
            @PathVariable Long articleId,
            Pageable pageable) {
        List<? extends CommentFacade> comments = commentService.getCommentsByArticle(() -> articleId, pageable);
        return ApiResponse.success(comments);
    }

    @AllowForAll
    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<?> getReplies(
            @PathVariable Long commentId,
            Pageable pageable) {
        List<? extends CommentFacade> comments = commentService.getReplies(() -> commentId, pageable);
        return ApiResponse.success(comments);
    }
}
