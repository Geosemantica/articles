package com.geosemantica.articleservice.dal.repositories;

import com.geosemantica.articleservice.dal.model.Article;
import com.geosemantica.articleservice.dal.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
            from Comment c
            where c.article = :article
            and c.isRemoved = false
            order by c.createdAt desc
            """)
    List<Comment> findByArticle(Article article, Pageable pageable);

    @Query("""
            from Comment c
            where c.replyTo = :commentId
            and c.isRemoved = false
            order by c.createdAt desc
            """)
    List<Comment> findReplies(Long commentId, Pageable pageable);

    @Query("""
            from Comment c
            where c.id = :id
            and c.isRemoved = false
            """)
    Optional<Comment> findById(Long id);

    @Query("""
            select count(c) > 0
            from Comment c
            where c.id = :id
            and c.isRemoved = false
            """)
    boolean existsById(Long id);

    @Modifying
    @Transactional
    @Query("""
            update Comment c
            set c.isRemoved = true
            where c = :comment
            and c.isRemoved = false
            """)
    int setRemoved(Comment comment);

    @Modifying
    @Transactional
    @Query("""
            update Comment c
            set c.isRemoved = true
            where c.authorId = :userId
            and c.isRemoved = false
            """)
    int setRemovedAll(UUID userId);
}
