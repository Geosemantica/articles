package com.geosemantica.articleservice.web.model.requests;

import com.geosemantica.articleservice.facades.model.CommentFacade;
import com.geosemantica.articleservice.web.model.dto.CommentDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.UUID;

@Getter
public class CommentRequest implements CommentFacade {
    private Long replyTo;
    @NotBlank
    @Length(max = 512)
    private String text;
    private UUID authorId;
    private Instant createdAt;
    private Long id;
}
