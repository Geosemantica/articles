package com.geosemantica.articleservice.web.model.dto;

import com.geosemantica.articleservice.facades.identities.CommentIdentity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentDto implements CommentIdentity {
    @NotNull
    private Long id;
}
