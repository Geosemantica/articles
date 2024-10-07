package com.geosemantica.articleservice.dal.model;

import jakarta.persistence.*;
import lombok.*;
import com.geosemantica.articleservice.facades.model.ArticleFacade;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Table(name = "articles")
@Entity
@Getter
@Setter
public class Article implements ArticleFacade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private UUID authorId;
    private Instant createdAt;

    private boolean isRemoved = false;

    @Builder
    public Article(String title, String text, UUID authorId, Instant createdAt) {
        this.title = title;
        this.text = text;
        this.authorId = authorId;
        this.createdAt = createdAt;
    }
}
