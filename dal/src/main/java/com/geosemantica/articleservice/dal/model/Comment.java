package com.geosemantica.articleservice.dal.model;

import jakarta.persistence.*;
import lombok.*;
import com.geosemantica.articleservice.facades.model.CommentFacade;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Table(name = "comments")
@Entity
@Getter
@Setter
public class Comment implements CommentFacade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long replyTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    private String text;
    private UUID authorId;
    private Instant createdAt;

    private boolean isRemoved = false;

    @Builder
    public Comment(Article article, Long replyTo, String text, UUID authorId, Instant createdAt) {
        this.replyTo = replyTo;
        this.article = article;
        this.text = text;
        this.authorId = authorId;
        this.createdAt = createdAt;
    }
}
